package com.nzefler.cart_service.controller;

import com.nzefler.cart_service.dto.CartRequestDTO;
import com.nzefler.cart_service.dto.CartResponseDTO;
import com.nzefler.cart_service.model.Cart;
import com.nzefler.cart_service.service.CartServiceImpl;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CartController {

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    public List<CartResponseDTO> getAllCarts(){
        return cartService.findAllCarts();
    }

    public CartResponseDTO getCartById(Long cartId) {
        return cartService.findCartById(cartId);
    }

    public List<CartResponseDTO> getAllCartByUserId(Long userId){
        return cartService.findAllCartByUserId(userId);
    }

    public CartResponseDTO createCart(CartRequestDTO cart){
        return cartService.saveCart(cart);
    }

    public CartResponseDTO updateCart(Cart cart){
        return cartService.updateCart(cart);
    }

    public Boolean deleteCart(Long cartId){
        return cartService.deleteCart(cartId);
    }

}
