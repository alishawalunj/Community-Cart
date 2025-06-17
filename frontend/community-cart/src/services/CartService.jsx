import axiosInstance from "../config/axiosInstance";

console.log("Cart Services called");

const CART_BASE_URL = "http://localhost:8083/carts";

export const getAllCarts = async() => {
    return await axiosInstance({
        method: 'get',
        url: `${CART_BASE_URL}/cart-service/cart/all`
    })
}

export const getAllCartByUserId = async(cartId) => {
    return await axiosInstance({
        method: 'get',
        url: `${CART_BASE_URL}/cart-service/getAllCartByUserId/${cartId}`
    })
}


export const createCart = async(cart) => {
    return await axiosInstance({
        method: 'post',
        url: `${CART_BASE_URL}/cart-service/createCart`,
        data: cart
    })
}

export const updateCart = async(cart) => {
    return await axiosInstance({
        method: 'put',
        url: `${CART_BASE_URL}/cart-service/updateCart`,
        data: cart
    })
}

export const deleteCart = async(cartId) => {
    return await axiosInstance({
        method: 'delete',
        url: `${CART_BASE_URL}/cart-service/deleteCart/${cartId}`
    })
}