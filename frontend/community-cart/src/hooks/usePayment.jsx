import {
    createPaymentService,
    updatePaymentService,
    getAllPaymentService,
    getPaymentByIdService,
    getPaymentsByUserIdService
} from '../services/PaymentService';
import { useState } from "react";

export const usePayment = () => {
    const [refreshFlag, setRefreshFlag ] = useState(false);
  
  const refresh = () => {
    setRefreshFlag(!refreshFlag);
  }

  const createPayment = async(payment) => {
   const response = await createPaymentService(payment);
   return response.data;
  }

  const updatePayment = async(payment) => {
   const response = await updatePaymentService(payment);
   return response.data;
  }

  const getAllPayment = async() => {
   const response = await getAllPaymentService();
   return response.data;
  }

  const getPaymentById = async(paymentId) => {
   const response = await getPaymentByIdService(paymentId);
   return response.data;
  }

  const getPaymentsByUserId = async(userId) => {
   const response = await getPaymentsByUserIdService(userId);
   return response.data;
  }


return { refresh, createPayment, updatePayment, getAllPayment, getPaymentById, getPaymentsByUserId };



}
