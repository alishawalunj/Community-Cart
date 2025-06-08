package com.nzefler.cart_service.service;

import com.nzefler.cart_service.mapper.CartMapper;
import com.nzefler.cart_service.dto.CartRequestDTO;
import com.nzefler.cart_service.dto.CartResponseDTO;
import com.nzefler.cart_service.exception.EntityNotFoundException;
import com.nzefler.cart_service.model.Cart;
import com.nzefler.cart_service.repository.CartRepository;
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
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CartResponseDTO findCartById(Long cartId) {
        try{
            return cartRepository.findById(cartId).map(this::toDTO).orElseThrow(() -> new EntityNotFoundException("Cart doesnt exist"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public List<CartResponseDTO> findAllCartByUserId(Long userId) {
        try{
            return cartRepository.findByUserId(userId).stream().map(this::toDTO).collect(Collectors.toList());
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public CartResponseDTO updateCart(Cart cart) {
        try {
            Cart existing = cartRepository.findById(cart.getCartId()).orElseThrow(() -> new EntityNotFoundException("Cart doesn't exist"));
            existing.setUserId(cart.getUserId());
            existing.setProductIds(cart.getProductIds());
            return toDTO(cartRepository.save(existing));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public Boolean deleteCart(Long cartId) {
        try {
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
            cartRepository.deleteById(cartId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
