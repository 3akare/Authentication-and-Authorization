import React, { createContext, useState, useEffect } from "react";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const login = (token, userName, userEmail) => {
    localStorage.setItem("token", token);
    localStorage.setItem("name", userName);
    localStorage.setItem("email", userEmail);
    setIsAuthenticated(true);
    setName(userName);
    setEmail(userEmail);
    setLoading(false);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("name");
    localStorage.removeItem("email");
    setIsAuthenticated(false);
    setName("");
    setEmail("");
  };

  useEffect(() => {
    const checkAuthStatus = () => {
      const token = localStorage.getItem("token");
      const storedName = localStorage.getItem("name");
      const storedEmail = localStorage.getItem("email");

      if (token) {
        setIsAuthenticated(true);
        setName(storedName || "");
        setEmail(storedEmail || "");
      } else {
        setIsAuthenticated(false);
      }

      setLoading(false);
    };

    checkAuthStatus();
  }, []);

  return (
    <AuthContext.Provider
      value={{
        isAuthenticated,
        setIsAuthenticated,
        loading,
        setLoading,
        name,
        setName,
        email,
        setEmail,
        login,
        logout
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
