import React, { useContext, useState } from 'react';
const AuthContext = React.createContext();

export function useAuth(){
    return useContext(AuthContext)
}

export function AuthProvider(props){
    const [ auth, setAuth ] = useState(() =>{
        const token = localStorage.getItem("auth");
        const userId = localStorage.getItem("userId");
        return token && userId ? { token, userId } : {};
    });
    
    const [ isLoggedIn, setIsLoggedIn ] = useState(() => !!localStorage.getItem("auth"));

    const logout = () => {
        localStorage.removeItem("auth");
        localStorage.removeItem("userId");
        setAuth({});
        setIsLoggedIn(false);
    }

    const value = { auth , setAuth, isLoggedIn, setIsLoggedIn, logout};
    return (
        <AuthContext.Provider value={value}>{props.children}</AuthContext.Provider>
    )
}