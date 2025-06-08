package com.nzefler.cart_service.mapper;

import com.nzefler.cart_service.dto.CartItemRequestDTO;
import com.nzefler.cart_service.dto.CartItemResponseDTO;
import com.nzefler.cart_service.dto.CartRequestDTO;
import com.nzefler.cart_service.dto.CartResponseDTO;
import com.nzefler.cart_service.model.Cart;
import com.nzefler.cart_service.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {


    public Cart toEntity(CartRequestDTO request){
        Cart cart = new Cart();
        cart.setUserId(request.getUserId());

        List<CartItem> cartItems = new ArrayList<>();
        if(request.getItems() != null && !request.getItems().isEmpty()){
            for(CartItemRequestDTO cartItemRequest : request.getItems()){
                CartItem cartItem = new CartItem();
                cartItem.setProductId(cartItemRequest.getProductId());
                cartItem.setCart(cart);
                cartItems.add(cartItem);
            }
        }

        cart.setItems(cartItems);
        return cart;
    }


    public CartResponseDTO toDTO(Cart cart) {
        CartResponseDTO response = new CartResponseDTO();
        response.setUserId(cart.getUserId());

        List<CartItemResponseDTO> itemDTOs = new ArrayList<>();
        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                CartItemResponseDTO itemDTO = new CartItemResponseDTO();
                itemDTO.setProductId(item.getProductId());
                itemDTOs.add(itemDTO);
            }
        }
        response.setItems(itemDTOs);

        return response;
    }


}
