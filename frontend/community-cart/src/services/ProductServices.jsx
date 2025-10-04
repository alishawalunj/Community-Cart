import axiosInstance from "./axiosInstance";


console.log("Product Services called");

const PRODUCT_BASE_URL = "http://localhost:8082/product-service";

export const getAllProductsService = async(inputs) => {
    return await axiosInstance({
        method: 'get',
        url: `${PRODUCT_BASE_URL}/getAllProducts`
    })
}

export const getProductByIdService = async(productId) => {
    return await axiosInstance({
        method: 'get',
        url: `${PRODUCT_BASE_URL}/getProductById/${productId}`
    })
}

export const getProductsByCommunityIdService = (communityId) => {
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
/**
 * 
 * @param {*} userId 
 * @returns list of products user will be selling
 */
export const getProductsByUserIdService = async(userId) => {
    return await axiosInstance({
        method: 'get',
        url:`${PRODUCT_BASE_URL}/getProductsByUserId/${userId}`
    })
}
/**
 * 
 * @param {*} userId 
 * @returns 
 */
export const getProductsByUserCommunitiesService = async (userId) => {
    try {
        const response = await axiosInstance({
            method: 'get',
            url: `${PRODUCT_BASE_URL}/getProductsByUserCommunities/${userId}`
        });
        return response.data;
    } catch (error) {
        console.error("Error from backend", error);
        return [];
    }
}
/**
 * 
 * @param {*} userId 
 * @returns list of products user will be buying from the communities they are enrolled into
 */
// export const getAllUsersProductsService = async(userId) => {
//     return await axiosInstance({
//         method: 'get',
//         url: `${PRODUCT_BASE_URL}/getAllUsersProducts/${userId}`
//     })
// }

export const createProductService = async (product) => {
    try {
        const response = await axiosInstance({
            method: 'post',
            url: `${PRODUCT_BASE_URL}/createProduct`,
            data: product
        });
        console.log("Response from backend", response);
        return response.data;
    } catch (error) {
        console.error("Error from backend", error);
    }
}

export const updateProductService = async (product) =>{
    try {
        const response = await axiosInstance({
            method: 'put',
            url: `${PRODUCT_BASE_URL}/updateProduct`,
            data: product
        });
        console.log("Response from backend", response);
        return response.data;
    } catch (error) {
        console.error("Error from backend", error);
    }
}

export const deleteProductService = async (productId) => {
    try {
        const response = await axiosInstance({
            method: 'delete',
            url: `${PRODUCT_BASE_URL}/deleteProduct/${productId}`
        });
        console.log("Response from Backend", response);
        return response.data;
    } catch (error) {
        console.error("Error from backend", error);
    }
}