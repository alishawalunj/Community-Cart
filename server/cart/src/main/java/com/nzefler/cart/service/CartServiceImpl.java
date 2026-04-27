package com.nzefler.cart.service;

import com.nzefler.cart.client.OrderServiceClient;
import com.nzefler.cart.client.ProductServiceClient;
import com.nzefler.cart.dto.CartItemRequestDTO;
import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.dto.CheckoutResponseDTO;
import com.nzefler.cart.enums.CartStatus;
import com.nzefler.cart.exception.EntityNotFoundException;
import com.nzefler.cart.exception.ErrorConstants;
import com.nzefler.cart.exception.InsufficientStockException;
import com.nzefler.cart.exception.UnAuthorizedException;
import com.nzefler.cart.mapper.CartMapper;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.model.CartItem;
import com.nzefler.cart.repository.CartItemRepository;
import com.nzefler.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final CartMapper cartMapper;
    private final OrderServiceClient orderServiceClient;

    public CartServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductServiceClient productServiceClient, CartMapper cartMapper, OrderServiceClient orderServiceClient) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productServiceClient = productServiceClient;
        this.cartMapper = cartMapper;
        this.orderServiceClient = orderServiceClient;
    }

    @Override
    public CartResponseDTO getOpenCart(Long userId) {
        Cart cart  = cartRepository.findByUserIdAndStatus(userId, CartStatus.OPEN).orElseGet(() -> createNewCart(userId));
        return cartMapper.toDTO(cart);
    }

    @Override
    public CartResponseDTO getCartById(Long cartId, Long requestingUserId) {
        Cart cart = findOrThrow(cartId);
        assertOwner(cart, requestingUserId);
        return cartMapper.toDTO(cart);
    }

    @Override
    public List<CartResponseDTO> getAllCarts(Long userId) {
        return cartRepository.findByUserId(userId).stream().map(cartMapper::toDTO).toList();
    }

    @Override
    public CartResponseDTO addItem(Long cartId, Long userId, CartItemRequestDTO request) {
        Cart cart = findOrThrow(cartId);
        assertOwner(cart, userId);
        ProductServiceClient.ProductDTO product = productServiceClient.getProduct(request.getProductId());
        if(product.getStockCount < request.getQuantity()){
            throw new InsufficientStockException(ErrorConstants.INSUFFICIENT_STOCK);
        }
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProductId(product.getProductId());
        item.setCommunityId(product.getCommunityId());
        item.setProductName(product.getName());
        item.setQuantity(request.getQuantity());
        item.setPriceAtAdd(product.getPrice());
        item.setAddedAt(LocalDateTime.now());
        cart.getCartItems().add(item);
        cart.setUpdatedAt(LocalDateTime.now());
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public CartResponseDTO updateItemQuantity(Long cartId, Long itemId, Long userId, CartItemRequestDTO request) {
        Cart cart = findOrThrow(cartId);
        assertOwner(cart, userId);

        CartItem item = cart.getCartItems().stream()
                .filter(i -> i.getCartItemId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorConstants.ITEM_NOT_FOUND));

        ProductServiceClient.ProductDTO product = productServiceClient.getProduct(item.getProductId());
        if (product.getStockCount() < request.getQuantity()) {
            throw new InsufficientStockException(ErrorConstants.INSUFFICIENT_STOCK);
        }

        item.setQuantity(request.getQuantity());
        cart.setUpdatedAt(LocalDateTime.now());
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public void removeItem(Long cartId, Long cartItemId, Long userId) {
        Cart cart = findOrThrow(cartId);
        assertOwner(cart, userId);
        cart.getCartItems().removeIf(i -> i.getCartItemId().equals(itemId));
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long cartId, Long userId) {
        Cart cart = findOrThrow(cartId);
        assertOwner(cart, userId);
        cart.getCartItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Override
    public CheckoutResponseDTO checkout(Long cartId, Long userId) {
        Cart cart = findOrThrow(cartId);
        assertOwner(cart, userId);

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException(ErrorConstants.CART_EMPTY);
        }

        cart.getCartItems().forEach(item -> productServiceClient.decrementStock(item.getProductId(), item.getQuantity()));

        BigDecimal total = cart.getCartItems().stream()
                .map(i -> i.getPriceAtAdd().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<OrderServiceClient.OrderItemDTO> orderItems = cart.getCartItems().stream()
                .map(i -> new OrderServiceClient.OrderItemDTO(
                        i.getProductId(), i.getProductName(), i.getQuantity(), i.getPriceAtAdd()))
                .collect(Collectors.toList());


        OrderServiceClient.OrderResponseDTO order = orderServiceClient.createOrder(new OrderServiceClient.OrderRequestDTO(userId, cartId, total, orderItems));
        cart.setStatus(CartStatus.CHECKED_OUT);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        return new CheckoutResponseDTO(order.getOrderId(), "Order placed successfully");
    }

    private Cart createNewCart(Long userId){
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setStatus(CartStatus.OPEN);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    private Cart findOrThrow(Long id){
        return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorConstants.CART_NOT_FOUND));
    }

    private void assertOwner(Cart cart, Long userId){
        if(!cart.getUserId().equals(userId)){
            throw new UnAuthorizedException(ErrorConstants.UNAUTHORIZED_CART);
        }
    }
}
