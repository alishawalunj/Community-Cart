import { useState } from "react";
import {
  getAllCartsService,
  getAllCartByUserIdService,
  getCartByIdService,
  createCartService,
  updateCartService,
  deleteCartService,
  addItemToCartService,
  removeItemFromCartService,
  clearCartService,
  checkoutCartService,
  getOpenCartService
} from "../services/CartService";

export const useCart = () => {
  const [refreshFlag, setRefreshFlag] = useState(false);

  const refresh = () => {
    setRefreshFlag(!refreshFlag);
  };

  //Get all carts (admin/debug)
  const getAllCarts = async () => {
    try {
      const response = await getAllCartsService();
      return response.data;
    } catch (error) {
      console.error("Error fetching all carts:", error);
      return [];
    }
  };

  //Get all carts by user
  const getAllCartByUserId = async (userId) => {
    try {
      const response = await getAllCartByUserIdService(userId);
      return response.data;
    } catch (error) {
      console.error("Error fetching carts for user:", error);
      return [];
    }
  };

  //Get specific cart
  const getCartById = async (cartId) => {
    try {
      const response = await getCartByIdService(cartId);
      console.log("Cart data:", response.data);
      return response.data;
    } catch (error) {
      console.error("Error fetching cart by ID:", error);
      return null;
    }
  };

  //Create a new cart
  const createCart = async (cart) => {
    try {
      const response = await createCartService(cart);
      refresh();
      return response.data;
    } catch (error) {
      console.error("Error creating cart:", error);
      return null;
    }
  };

  //Update cart
  const updateCart = async (cart) => {
    try {
      const response = await updateCartService(cart);
      refresh();
      return response.data;
    } catch (error) {
      console.error("Error updating cart:", error);
      return null;
    }
  };

  //Delete cart
  const deleteCart = async (cartId) => {
    try {
      await deleteCartService(cartId);
      refresh();
    } catch (error) {
      console.error("Error deleting cart:", error);
    }
  };

  //Add item
  const addItemToCart = async (userId, item) => {
    try {
      const response = await addItemToCartService(userId, item);
      refresh();
      return response.data;
    } catch (error) {
      console.error("Error adding item to cart:", error);
      return null;
    }
  };

  //Remove item
  const removeItemFromCart = async (cartId, cartItemId) => {
    try {
      const response = await removeItemFromCartService(cartId, cartItemId);
      refresh();
      return response.data;
    } catch (error) {
      console.error("Error removing item from cart:", error);
      return null;
    }
  };

  //Clear cart
  const clearCart = async (cartId) => {
    try {
      const response = await clearCartService(cartId);
      refresh();
      return response.data;
    } catch (error) {
      console.error("Error clearing cart:", error);
      return null;
    }
  };

  //Checkout cart → returns CartSummaryResponseDTO
  const checkoutCart = async (cart) => {
    try {
      const response = await checkoutCartService(cart);
      refresh();
      return response.data;
    } catch (error) {
      console.error("Error during checkout:", error);
      return null;
    }
  };

  const getOpenCart = async (userId) => {
    try {
      const response = await getOpenCartService(userId);
      refresh();
      return response.data;
    } catch (error) {
      console.error("Error during checkout:", error);
      return null;
    }
  };

  //Expose everything
  return {
    getAllCarts,
    getAllCartByUserId,
    getCartById,
    createCart,
    updateCart,
    deleteCart,
    addItemToCart,
    removeItemFromCart,
    clearCart,
    checkoutCart,
    getOpenCart,
    refreshFlag,
  };
};
