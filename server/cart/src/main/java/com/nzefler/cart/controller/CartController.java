package com.nzefler.cart.controller;

import com.nzefler.cart.dto.CartItemRequestDTO;
import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.dto.CheckoutResponseDTO;
import com.nzefler.cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    private Long currentUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/user/{userId}/open")
    public ResponseEntity<CartResponseDTO> getOpenCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getOpenCart(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id, currentUserId()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponseDTO>> getAllCarts(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getAllCarts(userId));
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<CartResponseDTO> addItem(
            @PathVariable Long id,
            @RequestBody CartItemRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addItem(id, currentUserId(), request));
    }

    @PutMapping("/{id}/items/{itemId}")
    public ResponseEntity<CartResponseDTO> updateItem(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @RequestBody CartItemRequestDTO request) {
        return ResponseEntity.ok(cartService.updateItem(id, itemId, currentUserId(), request));
    }

    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id, @PathVariable Long itemId) {
        cartService.removeItem(id, itemId, currentUserId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/items")
    public ResponseEntity<Void> clearCart(@PathVariable Long id) {
        cartService.clearCart(id, currentUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<CheckoutResponseDTO> checkout(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.checkout(id, currentUserId()));
    }

}
