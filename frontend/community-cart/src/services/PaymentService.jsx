import axiosInstance from "./axiosInstance";

console.log("Payment Service Called");

const PAYMENT_BASE_URL = "http://localhost:8084/payment-service";


// Payment Service

export const createPaymentService = async(payment) => {
    try{
        return await axiosInstance({
        method:'post',
        url:`${PAYMENT_BASE_URL}/createPayment`,
        data: payment
        });
    }catch (error) {
        console.error("Error from backend", error);
    } 
}

export const updatePaymentService = async(payment) => {
    try{
        return await axiosInstance({
        method:'put',
        url:`${PAYMENT_BASE_URL}/updatePayment`,
        data: payment
        });
    }catch (error) {
        console.error("Error from backend", error);
    } 
}

export const getAllPaymentService = async() => {
    try{
        return await axiosInstance({
        method:'get',
        url:`${PAYMENT_BASE_URL}/getAllPayments`
        });
    }catch (error) {
        console.error("Error from backend", error);
    }
}

export const getPaymentByIdService = async(paymentId) => {
    try{
        return await axiosInstance({
        method:'get',
        url:`${PAYMENT_BASE_URL}/getPaymentById/${paymentId}`
        });
    }catch (error) {
        console.error("Error from backend", error);
    }
}

export const getPaymentsByUserIdService = async(userId) => {
    try{
        return await axiosInstance({
        method:'get',
        url:`${PAYMENT_BASE_URL}/getPaymentsByUserId/user/${userId}`
        });
    }catch (error) {
        console.error("Error from backend", error);
    }
}
