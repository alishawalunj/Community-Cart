package com.nzefler.cart.service;

import com.nzefler.cart.client.AuthServiceClient;
import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.dto.CartSummaryResponseDTO;
import com.nzefler.cart.exception.EntityNotFoundException;
import com.nzefler.cart.exception.ErrorMessages;
import com.nzefler.cart.mapper.CartMapper;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.model.CartItem;
import com.nzefler.cart.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper mapper;
    private final AuthServiceClient client;

    public CartServiceImpl(CartRepository cartRepository, CartMapper mapper, AuthServiceClient client) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
        this.client = client;
    }

    private void validateToken(String token) {
        if (!client.isTokenValid(token)) {
            throw new RuntimeException("Unauthorized: Invalid token");
        }
    }

    @Override
    public List<CartResponseDTO> findAllCarts(String token) {
        validateToken(token);
        return cartRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponseDTO findCartById(Long cartId, String token) {
        validateToken(token);
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));
        return mapper.toDTO(cart);
    }

    @Override
    public List<CartResponseDTO> findAllCartByUserId(Long userId, String token) {
        validateToken(token);
        return cartRepository.findByUserId(userId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponseDTO findOpenCartByUserId(Long userId, String token) {
        validateToken(token);

        // Try to find an open cart directly
        Cart openCart = cartRepository.findByUserIdAndStatus(userId, "OPEN")
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setStatus("OPEN");
                    newCart.setCreatedAt(new Date());
                    newCart.setUpdatedAt(new Date());
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        return mapper.toDTO(openCart);
    }

    @Override
    public CartResponseDTO saveCart(Cart cart, String token) {
        validateToken(token);
        if (cart.getCartItems() != null) {
            cart.getCartItems().forEach(item -> item.setCart(cart));
        }
        Cart saved = cartRepository.save(cart);
        return mapper.toDTO(saved);
    }

    @Override
    public CartResponseDTO updateCart(Cart cart, String token) {
        validateToken(token);
        Cart existing = cartRepository.findById(cart.getCartId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));

        existing.setUserId(cart.getUserId());
        existing.setUpdatedAt(new Date());

        existing.getCartItems().clear();
        if (cart.getCartItems() != null) {
            cart.getCartItems().forEach(item -> item.setCart(existing));
            existing.getCartItems().addAll(cart.getCartItems());
        }

        Cart updated = cartRepository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public void deleteCart(Long cartId, String token) {
        validateToken(token);
        if (!cartRepository.existsById(cartId)) {
            throw new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST);
        }
        cartRepository.deleteById(cartId);
    }

    @Override
    public CartResponseDTO addItemToCart(Long userId, CartItem cartItem, String token) {
        validateToken(token);

        Cart cart = cartRepository.findByUserIdAndStatus(userId, "OPEN")
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setStatus("OPEN");
                    newCart.setCreatedAt(new Date());
                    newCart.setUpdatedAt(new Date());
                    return cartRepository.save(newCart);
                });

        cartItem.setAddedAt(new Date());
        cartItem.setCart(cart); // Important: maintain relationship

        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }
        cart.getCartItems().add(cartItem);
        cart.setUpdatedAt(new Date());

        Cart updated = cartRepository.save(cart);
        return mapper.toDTO(updated);
    }

    @Override
    public CartResponseDTO removeItemFromCart(Long cartId, Long cartItemId, String token) {
        validateToken(token);
        Cart existing = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));

        if (existing.getCartItems() == null || existing.getCartItems().isEmpty()) {
            throw new EntityNotFoundException("Cart item does not exist");
        }

        boolean removed = existing.getCartItems().removeIf(item -> item.getCartItemId().equals(cartItemId));
        if (!removed) {
            throw new EntityNotFoundException("Cart item does not exist");
        }

        existing.setUpdatedAt(new Date());
        Cart updated = cartRepository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public CartResponseDTO clearCart(Long cartId, String token) {
        validateToken(token);
        Cart existing = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));

        existing.setCartItems(new ArrayList<>());
        existing.setUpdatedAt(new Date());

        Cart updated = cartRepository.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public CartSummaryResponseDTO checkoutCart(Cart cart, String token) {
        validateToken(token);

        // Update cart status
        cart.setStatus("CHECKED_OUT");
        cart.setUpdatedAt(new Date());
        cartRepository.save(cart);

        // Build checkout summary
        CartSummaryResponseDTO response = new CartSummaryResponseDTO();
        response.setUserId(cart.getUserId());
        response.setCartId(cart.getCartId());

        double totalAmount = 0;
        List<Long> productIds = new ArrayList<>();

        if (cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
            for (CartItem item : cart.getCartItems()) {
                productIds.add(item.getProductId());
                double itemPrice = (item.getPrice() != 0) ? item.getPrice() : 10.0;
                totalAmount += item.getQuantity() * itemPrice;
            }
        }

        response.setProductIds(productIds);
        response.setAmount(totalAmount);
        return response;
    }
}
