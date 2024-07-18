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
import { useNavigate } from "react-router-dom"

const AuthCard = () => {
    const [isSignin, setIsSignIn] = useState(true);
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleUsername = (event) => {
        setUsername(event.target.value);
    }

    const handleEmail = (event) => {
        setEmail(event.target.value);
    }

    const handlePassword = (event) => {
        setPassword(event.target.value);
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        grecaptcha.ready(async () => {
            grecaptcha.execute('6LdENRMqAAAAABEnMnDBgH7gPcMxiiTB0If9JYC5', { action: 'homepage' })
                .then(async (token) => {
                    // console.log({ token })
                    
                });
        });

        try {
            const response = await axios.post(`http://localhost:8080/api/v1/auth/${isSignin ? "register" : "login"}`,
                { username, email, password })

            const { authToken } = response.data;
            localStorage.setItem("token", authToken);
            navigate("/dashboard")
        } catch (error) {
            console.log(error);
        }
    }

    const handleClick = () => {
        setIsSignIn((state) => !state);
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
                    <form className="grid gap-4" onSubmit={handleSubmit}>
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
                                {/* <p href="#" className="ml-auto inline-block text-sm underline">
                                Forgot your password?
                            </p> */}
                            </div>
                            <Input id="password" type="password" placeholder="Password" value={password} onChange={handlePassword} required />
                        </div>
                        <Button type="submit" className="w-full">
                            {isSignin ? "Register" : "Login"}
                        </Button>
                    </form>
                    <div className="flex w-full items-center justify-center gap-10">
                        <a href="http://localhost:8080/oauth2/authorization/google">
                            <Button variant="ghost" className="size-fit">
                                <GoogleIcon className={"size-6"} />
                            </Button>
                        </a>
                        <a href="http://localhost:8080/oauth2/authorization/github">
                            <Button variant="ghost" className="size-fit">
                                <GithubIcon className={"size-6"} />
                            </Button>
                        </a>
                        <a href="http://localhost:8080/oauth2/authorization/gitlab">
                            <Button variant="ghost" className="size-fit">
                                <GitLabIcon className={"size-6"} />
                            </Button>
                        </a>
                    </div>
                </div>
                <div className="mt-4 text-center text-sm">
                    {!isSignin ? "Don't have an account? " : "Already have an account? "}
                    {!isSignin ? <a href="#" className="underline" onClick={handleClick}>register</a> : <a href="#" className="underline" onClick={handleClick}>login</a>}

                </div>
            </CardContent>
        </Card>
    )
}

export default AuthCard;