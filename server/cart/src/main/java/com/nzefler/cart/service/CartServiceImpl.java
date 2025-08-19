package com.nzefler.cart.service;

import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.exception.EntityNotFoundException;
import com.nzefler.cart.exception.ErrorMessages;
import com.nzefler.cart.mapper.CartMapper;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.model.CartItem;
import com.nzefler.cart.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartMapper mapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper mapper) {
        this.cartRepository = cartRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CartResponseDTO> findAllCarts() {
        try{
            return cartRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public CartResponseDTO findCartById(Long cartId) {
       return cartRepository.findById(cartId).map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));
    }

    @Override
    public List<CartResponseDTO> findAllCartByUserId(Long userId) {
        try{
            return cartRepository.findByUserId(userId).stream().map(mapper::toDTO).collect(Collectors.toList());
        }catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public CartResponseDTO saveCart(Cart cart) {
        try{
            Cart saved = cartRepository.save(cart);
            return mapper.toDTO(saved);
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }
    @Override
    public CartResponseDTO updateCart(Cart cart) {
        try {
            Cart existing = cartRepository.findById(cart.getCartId()).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));
            existing.setUserId(cart.getUserId());
            existing.setUpdatedAt(cart.getUpdatedAt());
            existing.setCreatedAt(cart.getCreatedAt());
            existing.setCartItems(cart.getCartItems());
            return mapper.toDTO(cartRepository.save(existing));
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }


    @Override
    public void deleteCart(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST);
        }
        cartRepository.deleteById(cartId);
    }

    @Override
    public CartResponseDTO addItemToCart(Long cartId, CartItem cartItem) {
        try{
            Cart existing = cartRepository.findById(cartItem.getCartItemId()).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));
            if (existing.getCartItems() == null) {
                existing.setCartItems(new ArrayList<>());
            }
            cartItem.setCartId(cartId);
            existing.getCartItems().add(cartItem);
            return mapper.toDTO(cartRepository.save(existing));
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public CartResponseDTO removeItemFromCart(Long cartId, Long cartItemId) {
        try{
            if (cartId == null || cartItemId == null) {
                throw new IllegalArgumentException("Cart ID and Cart Item ID must not be null");
            }
            Cart existing = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart does not exist"));
            if (existing.getCartItems() != null) {
                boolean removed = existing.getCartItems().removeIf(item -> cartItemId.equals(item.getCartItemId()));
                if (!removed) {
                    throw new EntityNotFoundException("Cart Item does not exist");
                }
            } else {
                throw new EntityNotFoundException("Cart Item does not exist");
            }
            return mapper.toDTO(cartRepository.save(existing));
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public CartResponseDTO clearCart(Long cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID must not be null");
        }
        Cart existing = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart does not exist"));
        existing.setCartItems(new ArrayList<>());
        return mapper.toDTO(cartRepository.save(existing));
    }
}
