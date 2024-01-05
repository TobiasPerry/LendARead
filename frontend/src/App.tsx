import NavBar from "./components/NavBar.tsx";
import { Routes, Route, Outlet } from "react-router-dom";
import Landing from "./views/Landing.tsx";
import DiscoveryView from "./views/Discovery.tsx";
import NotFound from "./views/NotFound.tsx";


import LoginView from "./views/user/LogIn.tsx";
import Register from "./views/user/Register.tsx";
import ForgotPassword from "./views/user/ForgotPassword.tsx";
import UserHome from "./views/user/UserHome.tsx";
import {AuthProvider} from "./contexts/authContext.tsx";
import Locations from "./views/user/Locations.tsx";


export default function App() {
    return (
            <AuthProvider>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Landing />} />
                    <Route path="login" element={<LoginView />} />
                    <Route path="locations" element={<Locations />} />
                    <Route path="userHome" element={<UserHome />} />
                    <Route path="forgotpassword" element={<ForgotPassword />} />
                    <Route path="about" element={<About />} />
                    <Route path="register" element={<Register />} />
                    <Route path="dashboard" element={<Dashboard />} />
                    <Route path="discovery" element={<DiscoveryView/>} />
                    <Route path="user" element={<LoginView />} />
                    {/* Using path="*"" means "match anything", so this route
                acts like a catch-all for URLs that we don't have explicit
                routes for. */}
                    <Route path="*" element={<NotFound />} />
                </Route>
            </Routes>
            </AuthProvider>
    );
}
function Layout() {
    return (
        <div>
            <NavBar />
            {/* An <Outlet> renders whatever child route is currently active,
          so you can think about this <Outlet> as a placeholder for
          the child routes we defined above. */}
            <Outlet />
        </div>
    );
}

function About() {
    return (
        <div>
            <h2>About</h2>
        </div>
    );
}
function Dashboard() {
    return (
        <div>
            <h2>Dashboard</h2>
        </div>
    );
}
