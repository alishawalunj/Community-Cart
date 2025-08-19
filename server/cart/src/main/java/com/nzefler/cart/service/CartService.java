package com.nzefler.cart.service;

import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.model.CartItem;

import java.util.List;

public interface CartService {
    List<CartResponseDTO> findAllCarts();
    CartResponseDTO findCartById(Long cartId);
    List<CartResponseDTO> findAllCartByUserId(Long userId);
    CartResponseDTO saveCart(Cart cart);
    CartResponseDTO updateCart(Cart cart);
    void deleteCart(Long cartId);
    CartResponseDTO addItemToCart(Long cartId, CartItem cartItem);
    CartResponseDTO removeItemFromCart(Long cartId, Long cartItemId);
    CartResponseDTO clearCart(Long cartId);
}

