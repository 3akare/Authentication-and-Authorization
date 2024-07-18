import Container from "@/components/Container"
import AuthCard from "@/components/AuthCard"
import { useEffect } from "react"

const Login = () => {
  useEffect(() => {
    const script = document.createElement('script');
    script.src = "https://www.google.com/recaptcha/api.js?render=6LdENRMqAAAAABEnMnDBgH7gPcMxiiTB0If9JYC5"
    script.async = true

    document.body.appendChild(script);
    return () => {
      document.body.removeChild(script);
    }
  }, [])

  return (
    <>
      <Container>
        <AuthCard />
      </Container>
    </>
  )
}

export default Login