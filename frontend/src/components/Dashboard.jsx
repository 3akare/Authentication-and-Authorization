import React from 'react';
import { useAuth } from '@/contexts/AuthContext';

function Dashboard() {
    const { user, logout } = useAuth();

    if (!user) return <div>Loading...</div>;

    return (
        <div>
            <h1>Welcome, {user.name}!</h1>
            <button onClick={logout}>Logout</button>
        </div>
    );
}

export default Dashboard;