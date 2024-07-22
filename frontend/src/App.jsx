import { useEffect } from "react"

import { Routes, Route } from "react-router-dom"
import Home from "@/pages/Home"
import Login from "@/pages/Login"

export default function App() {
	useEffect(()=>{},[])
	return (
		<Routes>
			<Route path="/" element={<Login />}></Route>
		</Routes>
	)
}
