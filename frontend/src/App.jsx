import Login from "@/pages/Login"
import Dashboard from "@/pages/Dashboard"

import { useEffect } from "react"

import { Routes, Route, Navigate } from "react-router-dom"

export default function App() {
	useEffect(()=>{},[])
	return (
		<Routes>
			<Route path="/" element={<Login />}></Route>
			{/* <Route path="/" element={<Navigate to="/login" replace={true} />}></Route> */}
			<Route path="/dashboard" element={<Dashboard />}></Route>
		</Routes>
	)
}
