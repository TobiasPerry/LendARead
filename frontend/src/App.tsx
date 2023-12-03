import NavBar from "./components/NavBar.tsx";
import { Routes, Route, Outlet, Link } from "react-router-dom";
import Landing from "./views/Landing.tsx";
import DiscoveryView from "./views/Discovery.tsx";
import NotFound from "./views/NotFound.tsx";

import { createTheme, ThemeProvider } from '@mui/material/styles';
import LoginView from "./views/LogIn";

// Create a custom MUI theme
const theme = createTheme({
  palette: {
    primary: {
      main: '#2B3B2B',
    },
  },
});

export default function App() {
    return (
        <div>
            <ThemeProvider theme={theme}>
                <Routes>
                    <Route path="/" element={<Layout />}>
                        <Route index element={<Landing />} />
                        <Route path="about" element={<About />} />
                        <Route path="dashboard" element={<Dashboard />} />
                        <Route path="discovery" element={<DiscoveryView/>} />
                        <Route path="user" element={<LoginView />} />
                        {/* Using path="*"" means "match anything", so this route
                    acts like a catch-all for URLs that we don't have explicit
                    routes for. */}
                        <Route path="*" element={<NotFound />} />
                    </Route>
                </Routes>
            </ThemeProvider>
        </div>
    );
}
function Layout() {
    return (
        <div>
            <NavBar />
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