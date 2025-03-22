import React from "react";
import { useNavigate } from "react-router-dom";
const Login = () => {

    const navigate = useNavigate();

    const handleLogin = () => {
        navigate("/dashboard");
    }

    return (
        <>
        <div className="flex justify-center items-center  bg-transparent">
                <div className="w-96 bg-transparent p-6 rounded-lg shadow-lg">
                    
                    <div className="mb-4">
                        <label className="block mb-1 font-medium">Username</label>
                        <input type="text" className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2"/>
                    </div>
                    <div className="mb-4">
                        <label className="block mb-1 font-medium">Password</label>
                        <input type="password" className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2"/>
                    </div>
                    <div className="text-center">
                        <button className="w-1/4 bg-blue-500 text-white py-3 rounded-lg hover:bg-blue-600 transition" onClick={handleLogin}>Login</button>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Login;