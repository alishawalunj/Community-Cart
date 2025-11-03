import axiosInstance from "./axiosInstance";

console.log("Cart Services called");

const CART_BASE_URL = "http://localhost:8083/cart-service";

export const getAllCartsService = async() => {
    return await axiosInstance({
        method: 'get',
        url: `${CART_BASE_URL}/getAllCarts`
    })
}

export const getAllCartByUserIdService = async(userId) => {
    return await axiosInstance({
        method: 'get',
        url: `${CART_BASE_URL}/getAllCartByUserId/${userId}`
    })
}

export const getCartByIdService = async(cartId) => {
    return await axiosInstance({
        method: 'get',
        url: `${CART_BASE_URL}/getCartById/${cartId}`
    })
}


export const createCartService = async(cart) => {
    return await axiosInstance({
        method: 'post',
        url: `${CART_BASE_URL}/createCart`,
        data: cart
    })
}

export const updateCartService = async(cart) => {
    return await axiosInstance({
        method: 'put',
        url: `${CART_BASE_URL}/updateCart`,
        data: cart
    })
}

export const deleteCartService = async(cartId) => {
    return await axiosInstance({
        method: 'delete',
        url: `${CART_BASE_URL}/deleteCart/${cartId}`
    })
}

export const addItemToCartService = async(userId, item) => {
    return await axiosInstance({
        method: 'post',
        url: `${CART_BASE_URL}/addItem/cart/${userId}`,
        data: item
    })
}

export const removeItemFromCartService = async(cartId, cartItemId) => {
    return await axiosInstance({
        method: 'delete',
        url: `${CART_BASE_URL}/removeItem/cart/${cartId}/item/${cartItemId}`
    })
}

export const clearCartService = async(cartId) => {
    return await axiosInstance({
        method: 'delete',
        url: `${CART_BASE_URL}/clearCart/${cartId}`
    })
}

export const checkoutCartService = async(cart) => {
    return await axiosInstance({
        method: 'post',
        url: `${CART_BASE_URL}/checkoutCart`,
        data: cart
    })
}

export const getOpenCartService =  async(userId) => {
    return await axiosInstance({
        method: 'get',
        url: `${CART_BASE_URL}/getOpenCart/${userId}`,
    })
}