import { GoogleIcon, GithubIcon, GitLabIcon } from "@/components/Icons";
import { Button, buttonVariants } from "@/components/ui/button";
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

import { useState } from "react";

const AuthCard = () => {
  const [isSignin, setIsSignIn] = useState(true);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

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

  const githubAuthorizatonServer = async () => {
    console.log("here");
    if (
      !localStorage.getItem("token") ||
      localStorage.getItem("token") !== null
    )
      window.location.href = `https://github.com/login/oauth/authorize?client_id=${
        import.meta.env.VITE_GITHUB_CLIENT_ID
      }&scope=user`;
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
          <form className="grid gap-4">
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
            <Button type="submit" className="w-full">
              {isSignin ? "Register" : "Login"}
            </Button>
          </form>
          <div className="flex w-full items-center justify-center gap-10">
            <a
              href="#"
              variant="ghost"
              className={cn(buttonVariants({ variant: "ghost" }))}
            >
              <GoogleIcon className={"size-6"} />
            </a>
            <Button variant="ghost" onClick={githubAuthorizatonServer}>
              <GithubIcon className={"size-6"} />
            </Button>
            <a
              href="#"
              variant="ghost"
              className={cn(buttonVariants({ variant: "ghost" }))}
            >
              <GitLabIcon className={"size-6"} />
            </a>
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
