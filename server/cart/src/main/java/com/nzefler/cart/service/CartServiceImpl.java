package com.nzefler.cart.service;


import com.nzefler.cart.dto.CartRequestDTO;
import com.nzefler.cart.dto.CartResponseDTO;
import com.nzefler.cart.exception.EntityNotFoundException;
import com.nzefler.cart.exception.ErrorMessages;
import com.nzefler.cart.model.Cart;
import com.nzefler.cart.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    private CartResponseDTO toDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUserId());
        dto.setProductIds(cart.getProductIds());
        return dto;
    }

    @Override
    public List<CartResponseDTO> findAllCarts() {
        try{
            return cartRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public CartResponseDTO findCartById(Long cartId) {
       return cartRepository.findById(cartId).map(this::toDTO).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));
    }

    @Override
    public List<CartResponseDTO> findAllCartByUserId(Long userId) {
        try{
            return cartRepository.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
        }catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }

    @Override
    public CartResponseDTO saveCart(CartRequestDTO cart) {
        try{
            Cart newCart = new Cart();
            newCart.setUserId(cart.getUserId());
            newCart.setProductIds(cart.getProductIds());
            Cart saved = cartRepository.save(newCart);
            return toDTO(saved);
        }catch(Exception e){
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }
    @Override
    public CartResponseDTO updateCart(Cart cart) {
        try {
            Cart existing = cartRepository.findById(cart.getCartId()).orElseThrow(() -> new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST));
            existing.setUserId(cart.getUserId());
            existing.setProductIds(cart.getProductIds());
            return toDTO(cartRepository.save(existing));
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.ERROR_IN_PROCESSING);
        }
    }


    @Override
    public Boolean deleteCart(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new EntityNotFoundException(ErrorMessages.CART_DOES_NOT_EXIST);
        }
        cartRepository.deleteById(cartId);
        return true;
    }

}
