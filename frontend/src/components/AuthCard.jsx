import { AuthContext } from "@/context/AuthContext";
import { GoogleIcon, GithubIcon, GitLabIcon } from "@/components/icons";
import { Button, buttonVariants } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { cn } from "@/lib/utils";
import axios from "axios";

import { useState, useContext } from "react";

const AuthCard = () => {
  const { login } = useContext(AuthContext);
  const [isSignin, setIsSignIn] = useState(true);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleUsername = (event) => {
    setUsername(event.target.value);
  };

  const handleEmail = (event) => {
    setEmail(event.target.value);
  };

  const handlePassword = (event) => {
    setPassword(event.target.value);
  };

  const handleClick = () => {
    setIsSignIn((state) => !state);
  };

  const handleSubmit = async (type) => {
    try {
      let res;
      if (type === "Register") {
        res = await axios.post(`http://localhost:8080/api/auth/signup`, {
          name: username,
          password,
          email
        });
      }
      else {
        console.log(type)
        res = await axios.post(`http://localhost:8080/api/auth/login`, {
          email,
          password
        });
      }

      if (res.status === 200) {
        const { token, user, email } = res.data;
        login(token, user, email);
        navigate("/", { replace: true });
      }

    } catch (error) {
      console.error(error);
    }
  }

  const githubAuthorizatonServer = async () => {
    if (
      !localStorage.getItem("token") ||
      localStorage.getItem("token") !== null
    )
      window.location.href = `https://github.com/login/oauth/authorize?client_id=${import.meta.env.VITE_GITHUB_CLIENT_ID
        }&scope=user`;
  };

  const googleAuthorizationServer = async () => {
    if (
      !localStorage.getItem("token") ||
      localStorage.getItem("token") !== null
    )
      window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?redirect_uri=http://localhost:5173/login/callback&response_type=code&client_id=${import.meta.env.VITE_GOOGLE_CLIENT_ID}&scope=email%20profile`;
  };

  return (
    <Card className="mx-auto max-w-sm">
      <CardHeader>
        <CardTitle className="text-2xl">
          {isSignin ? "Sign Up" : "Login"}
        </CardTitle>
        <CardDescription>
          Enter your email below to {isSignin ? "register" : "log into"} your
          account
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div className="grid gap-4">
          <div className="grid gap-4">
            <div className="grid gap-2">
              {isSignin && (
                <>
                  <Label htmlFor="username">Username</Label>
                  <Input
                    id="Username"
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={handleUsername}
                    required
                  />
                </>
              )}
            </div>
            <div className="grid gap-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                placeholder="m@example.com"
                value={email}
                onChange={handleEmail}
                required
              />
            </div>
            <div className="grid gap-2">
              <div className="flex items-center">
                <Label htmlFor="password">Password</Label>
              </div>
              <Input
                id="password"
                type="password"
                placeholder="Password"
                value={password}
                onChange={handlePassword}
                required
                autoComplete="true"
              />
            </div>
            <Button type="submit" className="w-full" onClick={() => handleSubmit(isSignin ? "Register" : "Login")}>
              {isSignin ? "Register" : "Login"}
            </Button>
          </div>
          <div className="flex w-full items-center justify-center gap-10">
            <Button variant="ghost" onClick={googleAuthorizationServer}>
              <GoogleIcon className={"size-6"} />
            </Button>
            <Button variant="ghost" onClick={githubAuthorizatonServer}>
              <GithubIcon className={"size-6"} />
            </Button>
          </div>
        </div>
        <div className="mt-4 text-center text-sm">
          {!isSignin ? "Don't have an account? " : "Already have an account? "}
          {!isSignin ? (
            <span className="underline" onClick={handleClick}>
              register
            </span>
          ) : (
            <span className="underline" onClick={handleClick}>
              login
            </span>
          )}
        </div>
      </CardContent>
    </Card>
  );
};

export default AuthCard;
