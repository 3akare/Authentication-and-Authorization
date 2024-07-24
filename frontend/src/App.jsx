import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "@/pages/LoginPage";
import Dashboard from "@/pages/Dashboard";
import Callback from "@/components/Callback";
import { useContext } from "react";
import { AuthContext } from "@/context/AuthContext";
import ProtectedRoute from "@/components/ProtectedRoute";

const App = () => {
  const { loading } = useContext(AuthContext);

  return (
    <>
      {loading ? (
        <>loading...</>
      ) : (
        <Router>
          <Routes>
            <Route
              path="/"
              element={<ProtectedRoute element={<Dashboard />} />}
            />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/login/callback" element={<Callback />} />
            <Route path="*" element={<p>Not Found</p>} />
          </Routes>
        </Router>
      )}
    </>
  );
};

export default App;
