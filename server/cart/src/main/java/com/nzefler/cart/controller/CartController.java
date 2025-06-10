package com.nzefler.cart.controller;

import com.nzefler.cart.dto.CartRequestDTO;
import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.service.CartServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-service")
public class CartController {

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getAllCarts")
    public List<CartResponseDTO> getAllCarts(){
        return cartService.findAllCarts();
    }
    @GetMapping("/getCartById/{cartId}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Long cartId) {
        CartResponseDTO response = cartService.findCartById(cartId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getAllCartByUserId/{userId}")
    public List<CartResponseDTO> getAllCartByUserId(@PathVariable Long userId){
        return cartService.findAllCartByUserId(userId);
    }

    @PostMapping("/createCart")
    public ResponseEntity<CartResponseDTO> createCart(@RequestBody CartRequestDTO cart){
        CartResponseDTO response = cartService.saveCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/updateCart")
    public ResponseEntity<CartResponseDTO> updateCart(@RequestBody Cart cart){
        CartResponseDTO response= cartService.updateCart(cart);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteCart/{cartId}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable Long cartId){
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

}
