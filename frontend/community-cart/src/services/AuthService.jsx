import axiosInstance from "./axiosInstance";
import serviceConfig from "../config/Config";

const BASE = serviceConfig.userHost;

export const loginService =  async (data) => {
    const res = await axiosInstance.post(`${BASE}/auth/login`, data);
    return res.data;
}

export const refreshService = async () => {
    const res = await axiosInstance.post(`${BASE}/auth/refresh`);
    return res.data;
}

export const logoutService = async () => {
    await axiosInstance.post(`${BASE}/auth/logout`);
}