import React, { createContext, useState, useContext, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [user, setUser] = useState(null);

    useEffect(() => {
        if (token) {
            fetchUser();
        }
    }, [token]);

    // Fetch user data from the backend
    const fetchUser = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/user', {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            setUser(response.data);
        } catch (error) {
            console.error('Error fetching user:', error);
            logout();
        }
    };

    // Initiate GitHub OAuth flow
    const login = () => {
        window.location.href = `https://github.com/login/oauth/authorize?client_id=${import.meta.env.VITE_GITHUB_CLIENT_ID}&redirect_uri=http://localhost:5173/login/callback`;
    };

    // Handle OAuth callback
    const handleCallback = async (code) => {
        try {
            const response = await axios.post('http://localhost:8080/api/auth/github', { code });
            console.log(code)
            const { token } = response.data;
            console.log(token)
            setToken(token);
            localStorage.setItem('token', token);
        } catch (error) {
            console.error('Login failed:', error);
        }
    };

    // Logout user
    const logout = () => {
        setToken(null);
        setUser(null);
        localStorage.removeItem('token');
    };

    return (
        <AuthContext.Provider value={{ token, user, login, handleCallback, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);