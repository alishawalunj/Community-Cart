import React, { useContext, useState } from 'react';
const AuthContext = React.createContext();

export function useAuth(){
    return useContext(AuthContext)
}

export function AuthProvider(props){
    const [ auth, setAuth ] = useState(null);
    const [ isLoggedIn, setIsLoggedIn ] = useState(false);

    const value = { auth , setAuth, isLoggedIn, setIsLoggedIn};
    return (
        // eslint-disable-next-line react/prop-types
        <AuthContext.Provider value={value}>{props.children}</AuthContext.Provider>
    )
}