package com.nzefler.cart.mapper;

import com.nzefler.cart.dto.CartItemResponseDTO;
import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.model.CartItem;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartMapper() {
    }

    public CartResponseDTO toDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUserId());
        dto.setStatus(cart.getStatus());
        dto.setCreatedAt(cart.setCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        dto.setCartItems(cart.getCartItems().stream().map(this::toItemDTO).collect(Collectors.toList()));
        return dto;
    }

    public static CartItemResponseDTO toItemDTO(CartItem item){
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setCartItemId(item.getCartItemId());
        dto.setCommunityId(item.getCommunityId());
        dto.setAddedAt(item.getAddedAt());
        dto.setQuantity(item.getQuantity());
        dto.setProductId(item.getProductId());
        dto.setPriceAtAdd(item.getPriceAtAdd());
        dto.setProductName(item.getProductName());
        return dto;
    }
}
