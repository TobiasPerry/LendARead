import NavBar from "./components/NavBar.tsx";
import { Routes, Route, Outlet, Link } from "react-router-dom";
import Landing from "./views/Landing.tsx";
import DiscoveryView from "./views/Discovery.tsx";
import NotFound from "./views/NotFound.tsx";
export default function App() {
    return (
        <div>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Landing />} />
                    <Route path="about" element={<About />} />
                    <Route path="dashboard" element={<Dashboard />} />
                    <Route path="discovery" element={<DiscoveryView/>} />
                    {/* Using path="*"" means "match anything", so this route
                acts like a catch-all for URLs that we don't have explicit
                routes for. */}
                    <Route path="*" element={<NotFound />} />
                </Route>
            </Routes>
        </div>
    );
}
function Layout() {
    return (
        <div>
            <NavBar showSearchbar={true} />
            <hr />
            {/* An <Outlet> renders whatever child route is currently active,
          so you can think about this <Outlet> as a placeholder for
          the child routes we defined above. */}
            <Outlet />
        </div>
    );
}
function Home() {
    return (
        <div>
            <h2>Home</h2>
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
function NoMatch() {
    return (
        <div>
            <h2>Nothing to see here!</h2>
            <p>
                <Link to="/">Go to the home page</Link>
            </p>
        </div>
    );
}