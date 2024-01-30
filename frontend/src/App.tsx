import NavBar from "./components/NavBar.tsx";
import { Routes, Route, Outlet } from "react-router-dom";
import Landing from "./views/Landing.tsx";
import DiscoveryView from "./views/Discovery.tsx";
import NotFound from "./components/NotFound.tsx";
import React from 'react'; // Add this line


import LoginView from "./views/user/LogIn.tsx";
import Register from "./views/user/Register.tsx";
import ForgotPassword from "./views/user/ForgotPassword.tsx";
import UserHome from "./views/userAssets/UserAssets.tsx";
import AuthProvider from "./contexts/authContext.tsx";
import Locations from "./views/user/Locations.tsx";
import ViewAssetInstance from "./views/asset/ViewAssetInstance.tsx";
import UserAssetInstance from "./views/userAssets/UserAssetInstance.tsx";
import ReviewLender from "./views/reviews/ReviewLender.tsx";
import ReviewBorrower from "./views/reviews/ReviewBorrower.tsx";
import RequireAuth from "./components/RequireAuth.tsx";
import AddAsset from "./views/addAsset.tsx";
import ChangePassword from "./views/user/ChangePassword.tsx";
import UserDetails from "./views/user/UserProfile.tsx";
import UserProfileView from "./views/user/UserProfile.tsx";


export default function App() {
    return (
            <AuthProvider>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Landing />} />
                    <Route path="login" element={<LoginView />} />
                    <Route path="locations" element={<RequireAuth> <Locations /> </RequireAuth>} />
                    <Route path="userAssets" element={<RequireAuth> <UserHome /> </RequireAuth>} />
                    <Route path="forgotpassword" element={<ForgotPassword />} />
                    <Route path="changepassword" element={<ChangePassword />} />
                    <Route path="register" element={<Register />} />
                    <Route path="discovery" element={<DiscoveryView/>} />
                    <Route path="userHome" element={<UserProfileView />} />
                    <Route path="book/:bookNumber" element={<ViewAssetInstance /> } />
                    <Route path="userBook/:id" element={<RequireAuth> <UserAssetInstance /> </RequireAuth>} />
                    <Route path="review/lender/:lendingNumber" element={<RequireAuth> <ReviewLender /> </RequireAuth>} />
                    <Route path="review/borrower/:lendingNumber" element={<RequireAuth> <ReviewBorrower /> </RequireAuth>} />
                    <Route path="addAssetView" element={<RequireAuth> <AddAsset /> </RequireAuth>} />
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
            <Outlet />
        </div>
    );
}
