import React, { useContext, useState } from 'react';
const AuthContext = React.createContext();

export function useAuth(){
    return useContext(AuthContext)
}

export function AuthProvider(props){
    const [ auth, setAuth ] = useState(() =>{
        const stored = localStorage.getItem("auth");
        return stored ? JSON.parse(stored) : {};
    });
    
    const [ isLoggedIn, setIsLoggedIn ] = useState(() => !!localStorage.getItem("auth"));

    const logout = () => {
        localStorage.removeItem("auth");
        setAuth({});
        setIsLoggedIn(false);
    }

    const value = { auth , setAuth, isLoggedIn, setIsLoggedIn, logout};
    return (
        <AuthContext.Provider value={value}>{props.children}</AuthContext.Provider>
    )
}