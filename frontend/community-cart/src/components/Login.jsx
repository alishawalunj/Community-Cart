import React, { useState} from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { useUser } from "../hooks/useUser";


const Login = () => {
    const navigate = useNavigate();
    const { setAuth, setIsLoggedIn } = useAuth();
    const { login  } = useUser();

    const [ email, setEmail ] = useState('');
    const [ password, setPassword] = useState('');
    const [ error, setError ] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        if(email === ''){
            alert("Please enter email");
            return;
        }
        if(password === ''){
            alert("Please enter password");
            return;
        }
        const authRequest = {
            emailId: email,
            password: password
        };
        try{
            const user = await login(authRequest);
            if(!user){
                setError("User not found");
                return;
            }
            const userData = {
                userId : user.userId,
                token: user.token
            }
            setAuth(userData);
            // const response = JSON.stringify(userData);
            localStorage.setItem("auth", user.token);
            localStorage.setItem("userId", user.userId);
            setIsLoggedIn(true);
            navigate("/dashboard");
        }catch(error){
            console.log("Login error : ", error);
            setError(error.message);
        }
    };

    return (
        <>
            <div className="flex justify-center items-center bg-transparent">
                <div className="w-96 bg-transparent p-6 rounded-lg shadow-lg">
                    <div className="mb-4">
                        <label className="block mb-1 font-medium">Email</label>
                        <input 
                        type="email" 
                        onChange={(e)=>setEmail(e.target.value)}
                        value={email}
                        className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2" required/>
                    </div>
                    <div className="mb-4">
                        <label className="block mb-1 font-medium">Password</label>
                        <input 
                        type="password" 
                        onChange={(e)=>setPassword(e.target.value)}
                        value={password}
                        className="w-full px-3 py-2 border rounded-md focus:outline-none focus:ring-2" required/>
                    </div>
                    <div className="text-center">
                        <button type ="submit" className="w-1/4 bg-blue-500 text-white py-3 rounded-lg hover:bg-blue-600 transition" onClick={handleSubmit}>Login</button>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Login;