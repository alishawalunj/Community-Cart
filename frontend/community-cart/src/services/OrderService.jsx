import axiosInstance from "./axiosInstance";

console.log("Order Service Called");

const ORDER_BASE_URL = "http://localhost:8084/order-service";

// Order Service

export const createOrderService = async(order) => {
    try{
        return await axiosInstance({
        method:'post',
        url:`${ORDER_BASE_URL}/createOrder`,
        data: order
        });
    }catch (error) {
        console.error("Error from backend", error);
    } 
}

export const deleteOrderService = async(orderId) => {
    try{
        return await axiosInstance({
        method:'delete',
        url:`${ORDER_BASE_URL}/deleteOrder/${orderId}`
        });
    }catch (error) {
        console.error("Error from backend", error);
    } 
}

export const getAllOrderService = async() => {
    try{
        return await axiosInstance({
        method:'get',
        url:`${ORDER_BASE_URL}/getAllOrders`
        });
    }catch (error) {
        console.error("Error from backend", error);
    }
}

export const getOrderByIdService = async(orderId) => {
    try{
        return await axiosInstance({
        method:'get',
        url:`${ORDER_BASE_URL}/getOrderById/${orderId}`
        });
    }catch (error) {
        console.error("Error from backend", error);
    }
}

export const getAllOrdersByUserIdService = async(userId) => {
    try{
        return await axiosInstance({
        method:'get',
        url:`${ORDER_BASE_URL}/getAllOrdersByUserId/${userId}`
        });
    }catch (error) {
        console.error("Error from backend", error);
    }
}