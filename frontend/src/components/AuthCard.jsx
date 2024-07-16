import { GoogleIcon, GithubIcon } from "@/components/icons"
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

import { useState } from "react";

const AuthCard = () => {
    const [isSignin, setIsSignIn] = useState(true);

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
                    Enter your email below to {isSignin?"register":"log into"} your account
                </CardDescription>
            </CardHeader>
            <CardContent>
                <div className="grid gap-4">
                    <div className="grid gap-2">
                        <Label htmlFor="email">Email</Label>
                        <Input
                            id="email"
                            type="email"
                            placeholder="m@example.com"
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
                        <Input id="password" type="password" required />
                    </div>
                    <Button type="submit" className="w-full">
                        Login
                    </Button>
                    <div className="flex w-full items-center justify-center gap-20">
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
                    </div>
                </div>
                <div className="mt-4 text-center text-sm">
                    {!isSignin? "Don't have an account? ": "Already have an account? "}
                    {!isSignin ? <a href="#">register</a> : <a href="#">login</a>}
                    
                </div>
            </CardContent>
        </Card>
    )
}

export default AuthCard;