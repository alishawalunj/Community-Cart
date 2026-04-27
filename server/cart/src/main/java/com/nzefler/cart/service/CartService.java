package com.nzefler.cart.service;

import com.nzefler.cart.dto.CartItemRequestDTO;
import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.dto.CheckoutResponseDTO;
import java.util.List;

public interface CartService {
    CartResponseDTO getOpenCart(Long userId);
    CartResponseDTO getCartById(Long cartId, Long requestingUserId);
    List<CartResponseDTO> getAllCarts(Long userId);
    CartResponseDTO addItem(Long cartId, Long userId, CartItemRequestDTO request);
    CartResponseDTO updateItemQuantity(Long cartId, Long itemId, Long userId, CartItemRequestDTO request);
    void removeItem(Long cartId, Long cartItemId, Long userId);
    void clearCart(Long cartId, Long userId);
    CheckoutResponseDTO checkout(Long cartId, Long userId);

}

