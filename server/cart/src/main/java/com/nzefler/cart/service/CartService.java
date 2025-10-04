package com.nzefler.cart.service;

import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.model.CartItem;

import java.util.List;

public interface CartService {
    List<CartResponseDTO> findAllCarts(String token);
    CartResponseDTO findCartById(Long cartId, String token);
    List<CartResponseDTO> findAllCartByUserId(Long userId, String token);
    CartResponseDTO saveCart(Cart cart, String token);
    CartResponseDTO updateCart(Cart cart, String token);
    void deleteCart(Long cartId, String token);
    CartResponseDTO addItemToCart(Long cartId, CartItem cartItem, String token);
    CartResponseDTO removeItemFromCart(Long cartId, Long cartItemId, String token);
    CartResponseDTO clearCart(Long cartId, String token);
}

