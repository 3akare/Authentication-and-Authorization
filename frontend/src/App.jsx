import { BrowserRouter as Router, Routes, Route, Navigate, useSearchParams } from 'react-router-dom';
import axios from 'axios';

import { AuthProvider } from '@/contexts/AuthContext';
import Login from "@/components/Login"
// import Callback from '@/components/Callback';
import Dashboard from '@/components/Dashboard';
import PrivateRoute from '@/components/PrivateRoute';
import { useEffect } from 'react';

const Callback = (code) => {
	const [callback, SetCalback] = useSearchParams();

	const serverCall = async () => {
		const response = await axios.post("http://localhost:8080/api/auth/github", {
			code: callback.get("code")
		})
		console.log(response.data)
	}

	serverCall()
	
	return <p>{callback.get("code")}</p>
}

function App() {
	return (
		<Router>
			<Routes>
				<Route path='/' element={<a href="https://github.com/login/oauth/authorize?client_id=Ov23limIIiCD55RC0HXp&scope=user">Fuck</a>} />
				<Route path="/callback" Component={Callback} />
			</Routes>
		</Router>
		// <AuthProvider>
		// 	<Router>
		// 		<Routes>
		// 			<Route path="/" element={<Login />} />
		// 			<Route path="/login/callback" element={<Callback />} />
		// 			<Route
		// 				path="/dashboard"
		// 				element={
		// 					<PrivateRoute>
		// 						<Dashboard />
		// 					</PrivateRoute>
		// 				}
		// 			/>
		// 			<Route path="*" element={<Navigate to="/" replace />} />
		// 		</Routes>
		// 	</Router>
		// </AuthProvider>
	);
}
export default App;
