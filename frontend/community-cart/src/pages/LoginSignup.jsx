import React from "react";
import Login from "../components/Login";
import Signup from "../components/Signup";

const LoginSignup = () => {
    const [isLogin, setIsLogin] = React.useState(true);
    return(
        <>
            <div className="flex justify-center items-center h-screen bg-gradient-to-b from-blue-400 via-red-500 to-pink-500">
                <div className="w-96 bg-transparent p-6 rounded-lg shadow-lg border-2 border-white">
                   <div className="flex justify-around mb-4">
                    <span className="{text-2xl font-bold text-center text-white cursor-pointer px-4 ${isLogin ? 'underline' : ''}}" onClick={() => setIsLogin(true)}>Login</span>
                    <span className="{text-2xl font-bold text-center text-white cursor-pointer px-4 ${!isLogin ? 'underline' : ''}}" onClick={() => setIsLogin(false)}>Signup</span>
                   </div>
                    <div className="mt-4">
                        {isLogin ? <Login /> : <Signup />}
                    </div>
                </div>
            </div>
        </>
    )
}

export default LoginSignup;