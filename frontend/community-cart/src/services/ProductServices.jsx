import axiosInstance from "../config/axiosInstance";


console.log("Product Services called");

const PRODUCT_BASE_URL = "http://localhost:8082/products";

export const getAllProducts = async(inputs) => {
    return await axiosInstance({
        method: 'get',
        url: `${PRODUCT_BASE_URL}/getAllProducts`
    })
}

export const getProductById = async(productId) => {
    return await axiosInstance({
        method: 'get',
        url: `${PRODUCT_BASE_URL}/getProductById/${productId}`
    })
}

export const getProductsByCommunityId = (communityId) => {
    return axiosInstance({
        method: 'get',
        url: `${PRODUCT_BASE_URL}/getProductsByCommunityId/${communityId}`
    })
    .then(response => {
        console.log("Response from backend", response);
        return response.data;
    })
    .catch(error => {
        console.error("Error from backend", error);
    })
}

export const getProductsByUserId = async(userId) => {
    return await axiosInstance({
        method: 'get',
        url:`${PRODUCT_BASE_URL}/getProductsByUserId/{userId}`
    })
}

export const createProduct = (product) => {
    return axiosInstance({
        method:'post',
        url:`${PRODUCT_BASE_URL}/createProduct`,
        data: product
    })
    .then(response => {
        console.log("Response from backend", response);
        return response.data;
    })
    .catch(error => {
        console.error("Error from backend", error);
    })
}

export const updateProduct = (product) =>{
    return axiosInstance({
        method: 'put',
        url: `${PRODUCT_BASE_URL}/updateProduct`,
        data: product
    })
    .then(response => {
        console.log("Response from backend", response)
        return response.data;
    })
    .catch(error => {
        console.error("Error from backend", error);
    })
}

export const deleteProduct = (productId) => {
    return axiosInstance({
        method: 'delete',
        url: `${PRODUCT_BASE_URL}/deleteProduct/${productId}`
    })
    .then(response => {
        console.log("Response from Backend", response);
        return response.data;
    })
    .catch(error => {
        console.error("Error from backend", error);
    })
}