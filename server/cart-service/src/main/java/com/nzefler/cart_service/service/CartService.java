package com.nzefler.cart_service.service;

import com.nzefler.cart_service.dto.CartRequestDTO;
import com.nzefler.cart_service.dto.CartResponseDTO;
import com.nzefler.cart_service.model.Cart;

import java.util.List;

public interface CartService {

    List<CartResponseDTO> findAllCarts();
    CartResponseDTO findCartById(Long cartId);
    List<CartResponseDTO> findAllCartByUserId(Long userId);
    CartResponseDTO saveCart(CartRequestDTO cart);
    CartResponseDTO updateCart(Cart cart);
    Boolean deleteCart(Long cartId);

}
