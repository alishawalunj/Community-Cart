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
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }


    @Override
    public List<CartResponseDTO> findAllCarts() {
        try{
            return cartRepository.findAll().stream().map((cartMapper::toDTO)).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CartResponseDTO findCartById(Long cartId) {
        try{
            return cartRepository.findById(cartId).map(cartMapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Cart doesnt exist"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public List<CartResponseDTO> findAllCartByUserId(Long userId) {
        try{
            return cartRepository.findAll().stream()
                    .filter(cart -> cart.getUserId().equals(userId))
                    .map(cartMapper::toDTO)
                    .collect(Collectors.toList());
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CartResponseDTO saveCart(CartRequestDTO cart) {
        try{
            Cart savedCart = cartRepository.save(cartMapper.toEntity(cart));
            return cartMapper.toDTO(savedCart);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public CartResponseDTO updateCart(Cart cart) {
        try {
            Cart existingCart = cartRepository.findById(cart.getCartId())
                    .orElseThrow(() -> new EntityNotFoundException("Cart doesn't exist"));

            existingCart.setUserId(cart.getUserId());
            existingCart.setItems(cart.getItems());

            Cart updated = cartRepository.save(existingCart);
            return cartMapper.toDTO(updated);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public Boolean deleteCart(Long cartId) {
        try {
            Cart cart = cartRepository.findById(cartId)
                    .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
            cartRepository.delete(cart);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
