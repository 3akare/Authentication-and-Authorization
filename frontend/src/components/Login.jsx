import React from 'react';
import { useAuth } from '@/contexts/AuthContext';

function Login() {
  const { login } = useAuth();
  return <button onClick={login}>Login with GitHub</button>;
}

export default Login;