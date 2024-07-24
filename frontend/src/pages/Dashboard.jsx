import Container from "@/components/Container";
import { Button } from "@/components/ui/button";
import { AuthContext } from "@/context/AuthContext";
import React, { useContext } from "react";

const Dashboard = () => {
  const { name, logout } = useContext(AuthContext);
  return (
    <Container>
      <h1>Admin Dashboard, welcome {name}</h1>
      <Button onClick={logout}>logout</Button>
    </Container>
  );
};

export default Dashboard;
