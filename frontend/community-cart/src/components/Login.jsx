import React from "react";
import { Form } from "react-router-dom";
const Login = () => {
    return (
        <>
            <div className="flex justify-center items-center min-h-screen bg-gray-100">
                <div className="w-full max-w-md  h-1/2 p-6 bg-white rounded-lg shadow-md flex flex-col gap-4 justify-center mt-10 rounded-full">
                <h1 className="text-4xl font-bold flex justify-center mb-5">Login</h1>
                <form className="flex flex-col gap-5 items-center" method="post" action="/login">
                    <input type="text" name="username" placeholder="Username" className="border px-4 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"/> 
                    <input type="password" name="password" placeholder="Password" className="border px-4 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"/>
                    <button type ="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold rouded-full px-4 py-2">Login</button>
                </form>
                <div className="text-center mt-4">
                    <span className="text-gray-600">Don't have an account? </span>
                    <a href="/register" className="text-blue-500 hover:underline">Sign up</a>
                </div>
                </div>
                
            </div>
            
        </>
    );
};

export default Login;