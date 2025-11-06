import {
    createOrderService,
    deleteOrderService,
    getAllOrderService,
    getOrderByIdService,
    getAllOrdersByUserIdService
} from '../services/PaymentService';
import { useState } from "react";

export const useOrder = () => {
    const [refreshFlag, setRefreshFlag ] = useState(false);
  
  const refresh = () => {
    setRefreshFlag(!refreshFlag);
  }

  const createOrder = async(order) => {
   const response = await createOrderService(order);
   return response.data;
  }

  const updateOrder = async(orderId) => {
   const response = await deleteOrderService(orderId);
   return response.data;
  }

  const getAllOrder = async() => {
   const response = await getAllOrderService();
   return response.data;
  }

  const getOrderById = async(orderId) => {
   const response = await getOrderByIdService(orderId);
   return response.data;
  }

  const getOrderByUserId = async(userId) => {
   const response = await getAllOrdersByUserIdService(userId);
   return response.data;
  }

return { refresh, createOrder, updateOrder, getAllOrder, getOrderById, getOrderByUserId };

}
