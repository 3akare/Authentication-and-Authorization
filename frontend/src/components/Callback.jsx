import { AuthContext } from "@/context/AuthContext";
import axios from "axios";
import { useContext, useEffect, useRef } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import Loader from "./Loader";

const Callback = () => {
  const baseUrl = "http://localhost:8080";

  const { login, setLoading } = useContext(AuthContext);
  const [callbackParams] = useSearchParams();
  const navigate = useNavigate();
  const codeProcessed = useRef(false);

  const getJWTToken = async (code) => {
    try {
      setLoading(true);

      const res = code.length > 20 ?
        await axios.post(`${baseUrl}/api/auth/google`, { code }) : await axios.post(`${baseUrl}/api/auth/github`, { code });
      if (res.status === 200) {
        const { token, user, email } = res.data;
        login(token, user, email);
        navigate("/", { replace: true });
      }
    } catch (error) {
      console.error("Error", error);
      setLoading(false);
    }
  };

  useEffect(() => {
    const code = callbackParams.get("code");
    if (code && !codeProcessed.current) {
      console.log("code flow token: ", code);
      getJWTToken(code);
      codeProcessed.current = true;
    }
  }, [callbackParams]);

  return <Loader />;
};

export default Callback;
