import axios from "axios";
import serviceConfig from "../config/Config";

const axiosInstance = axios.create({
    withCredentials : true,
});

axiosInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("accessToken");
        console.log("token from interceptor",token);
        if(token){
            config.headers.Authorization = `Bearer ${token}`
        }
        return config;
    },
    (error) => Promise.reject(error)
);

export default axiosInstance;