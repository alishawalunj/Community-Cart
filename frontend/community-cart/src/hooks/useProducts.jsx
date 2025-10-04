import {
  getAllProductsService,
  getProductByIdService,
  getProductsByCommunityIdService,
  getProductsByUserIdService,
  getProductsByUserCommunitiesService,
  createProductService,
  updateProductService,
  deleteProductService
} from "../services/ProductServices";
import { useState } from "react";

export const useProducts = () => {
  const [refreshFlag, setRefreshFlag ] = useState(false);
  
  const refresh = () => {
    setRefreshFlag(!refreshFlag);
  }

  const getAllProducts = async() => {
    try{
        const response = await getAllProductsService();
        return response.data;
    }catch(error){
        console.error("Error from backend", error);
        return [];
    }
  }

  const getProductById = async(productId) => {
    try{
        const response = await getProductByIdService(productId);
        return response.data;
    }catch(error){
        console.error("Error from backend", error);
        return [];
    }
  }

  const getProductsByCommunityId = async(communityId) => {
    try{
        const response = await getProductsByCommunityIdService(communityId);
        return response.data;
    }catch(error){
        console.error("Error from backend", error);
        return [];
    }
  }
 
  const getProductsByUserId = async(userId) => {
    try{
        const response = await getProductsByUserIdService(userId);
        return response.data;
    }catch(error){
        console.error("Error from backend", error);
        return [];
    }
  }

  const getProductsByUserCommunities = async(userId) => {
    try{
        const response = await getProductsByUserCommunitiesService(userId);
        return response;
    }catch(error){
        console.error("Error from backend", error);
        return [];
    }
  }

  const createProduct = async(product) => {
    try{
        const response = await createProductService(product);
        return response.data;
    }catch(error){
        console.error("Error from backend", error);
        return [];
    }
  }

  const updateProduct = async(product) => {
    try{
        const response = await updateProductService(product);
        return response.data;
    }catch(error){
        console.error("Error from backend", error);
        return [];
    }
  }

  const deleteProduct = async(productId) => {
    try{
        const response = await deleteProductService(productId);
        return response.data;
    }catch(error){
        console.error("Error from backend", error);
        return [];
    }
  }

  return {refresh, getAllProducts, getProductById, getProductsByCommunityId, getProductsByUserId, getProductsByUserCommunities, createProduct, updateProduct, deleteProduct};

}