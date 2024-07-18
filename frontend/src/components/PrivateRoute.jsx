// src/components/PrivateRoute.js
import React from 'react';
import { Route, Redirect } from 'react-router-dom';

// Function to check if the user is authenticated
const isAuthenticated = () => {
    // Check for JWT token in localStorage or cookies
    const token = localStorage.getItem('token');
    return token !== null;
};

const PrivateRoute = ({ component: Component, ...rest }) => (
    <Route
        {...rest}
        render={props =>
            isAuthenticated() ? (
                <Component {...props} />
            ) : (
                <Redirect to="/login" />
            )
        }
    />
);

export default PrivateRoute;
