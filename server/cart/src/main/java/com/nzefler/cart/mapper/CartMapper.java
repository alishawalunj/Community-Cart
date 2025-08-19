package com.nzefler.cart.mapper;

import com.nzefler.cart.dto.CartItemResponseDTO;
import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.model.CartItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class CartMapper {

    public CartResponseDTO toDTO(Cart cart){
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUserId());
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        List<CartItemResponseDTO> itemList = new ArrayList<>();
        if(cart.getCartItems() != null && !cart.getCartItems().isEmpty()){
            itemList = cart.getCartItems().stream().map(item -> {
                CartItemResponseDTO itemDto = new CartItemResponseDTO();
                itemDto.setCartItemId(item.getCartItemId());
                itemDto.setProductId(item.getProductId());
                itemDto.setAddedAt(item.getAddedAt());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setCartId(item.getCartId());
                return itemDto;
            }).collect(Collectors.toList());
        }
        dto.setCartItems(itemList);
        return dto;
    }
}
