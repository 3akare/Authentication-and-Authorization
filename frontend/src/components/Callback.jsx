import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '@/contexts/AuthContext';

function Callback() {
    const navigate = useNavigate();
    const location = useLocation();
    const { handleCallback } = useAuth();

    useEffect(() => {
        const code = new URLSearchParams(location.search).get('code');
        if (code) {
            handleCallback(code).then(() => {
                navigate('/dashboard');
            });
        }
    }, [location, navigate, handleCallback]);

    return <div>Processing login...</div>;
}

export default Callback;