package com.nzefler.cart.controller;

import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.model.CartItem;
import com.nzefler.cart.service.CartServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<CartResponseDTO> getAllCarts(@RequestHeader("Authorization") String token){
        return cartService.findAllCarts(token);
    }

    @GetMapping("/getCartById/{cartId}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Long cartId, @RequestHeader("Authorization") String token) {
        CartResponseDTO response = cartService.findCartById(cartId, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/getAllCartByUserId/{userId}")
    public List<CartResponseDTO> getAllCartByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String token){
        return cartService.findAllCartByUserId(userId, token);
    }

    @PostMapping("/createCart")
    public ResponseEntity<CartResponseDTO> createCart(@RequestBody Cart cart, @RequestHeader("Authorization") String token){
        CartResponseDTO response = cartService.saveCart(cart, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/updateCart")
    public ResponseEntity<CartResponseDTO> updateCart(@RequestBody Cart cart, @RequestHeader("Authorization") String token){
        CartResponseDTO response= cartService.updateCart(cart, token);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/deleteCart/{cartId}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable Long cartId, @RequestHeader("Authorization") String token){
        cartService.deleteCart(cartId, token);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/addItem/cart/{cartId}/item/{item}")
    public CartResponseDTO addItem(@PathVariable Long cartId, @RequestBody CartItem item, @RequestHeader("Authorization") String token){
        return cartService.addItemToCart(cartId,item, token);
    }
    @DeleteMapping("/removeItem/cart/{cartId}/item/{cartItemId}")
    public CartResponseDTO removeItem(@PathVariable Long cartId, @PathVariable Long cartItemId, @RequestHeader("Authorization") String token){
        return cartService.removeItemFromCart(cartId, cartItemId, token);
    }
    @DeleteMapping("/clearCart/{cartId}")
    public CartResponseDTO clearCart(@PathVariable Long cartId, @RequestHeader("Authorization") String token){
        return cartService.clearCart(cartId, token);
    }
}
