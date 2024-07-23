import { GoogleIcon, GithubIcon, GitLabIcon } from "@/components/icons"
import { Button } from "@/components/ui/button"
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import axios from "axios"
import { useState } from "react";

const AuthCard = () => {
    const [isSignin, setIsSignIn] = useState(true);
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleUsername = (event) => {
        setUsername(event.target.value);
    }

    const handleEmail = (event) => {
        setEmail(event.target.value);
    }

    const handlePassword = (event) => {
        setPassword(event.target.value);
    }

    const handleClick = () => {
        setIsSignIn((state) => !state);
    }

    const oauthLogin = async (provider) => {
        let authUrl;

        switch (provider) {
            case "google":
                authUrl = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${import.meta.env.VITE_GOOGLE_CLIENT_ID}&redirect_uri=http://localhost:8080/login/callback&response_type=code&scope=email profile`;
                break;
            case "github":
                authUrl = `https://github.com/login/oauth/authorize?client_id=${import.meta.env.VITE_GITHUB_CLIENT_ID}&redirect_uri=http://localhost:8080/login/callback`;
                break;
            case "gitlab":
                authUrl = `https://gitlab.com/oauth/authorize?client_id=${import.meta.env.VITE_GITLAB_CLIENT_ID}&redirect_uri=http://localhost:8080/login/callback&response_type=code&scope=read_user`;
                break;
            default:
                throw new Error("Unsupported provider");
        }

        window.location.href = authUrl;
    }

    return (

        <Card className="mx-auto max-w-sm">
            <CardHeader>
                <CardTitle className="text-2xl">{
                    isSignin ? "Sign Up" : "Login"}
                </CardTitle>
                <CardDescription>
                    Enter your email below to {isSignin ? "register" : "log into"} your account
                </CardDescription>
            </CardHeader>
            <CardContent>
                <div className="grid gap-4">
                    <form className="grid gap-4">
                        <div className="grid gap-2">
                            {isSignin && <>
                                <Label htmlFor="username">Username</Label>
                                <Input
                                    id="Username"
                                    type="text"
                                    placeholder="Username"
                                    value={username}
                                    onChange={handleUsername}
                                    required
                                /></>}
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
                            <Input id="password" type="password" placeholder="Password" value={password} onChange={handlePassword} required />
                        </div>
                        <Button type="submit" className="w-full">
                            {isSignin ? "Register" : "Login"}
                        </Button>
                    </form>
                    <div className="flex w-full items-center justify-center gap-10">
                        <Button variant="ghost" className="size-fit" onClick={() => oauthLogin("google")}>
                            <GoogleIcon className={"size-6"} />
                        </Button>
                        <Button variant="ghost" className="size-fit" onClick={() => oauthLogin("github")}>
                            <GithubIcon className={"size-6"} />
                        </Button>
                        <Button variant="ghost" className="size-fit" onClick={() => oauthLogin("gitlab")}>
                            <GitLabIcon className={"size-6"} />
                        </Button>
                    </div>
                </div>
                <div className="mt-4 text-center text-sm">
                    {!isSignin ? "Don't have an account? " : "Already have an account? "}
                    {!isSignin ? <span className="underline" onClick={handleClick}>register</span> : <span className="underline" onClick={handleClick}>login</span>}
                </div>
            </CardContent>
        </Card>
    )
}

export default AuthCard;