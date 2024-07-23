import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

function PrivateRoute({ children }) {
    const { token } = useAuth();
    return token ? children : <Navigate to="/" replace />;
}

export default PrivateRoute;