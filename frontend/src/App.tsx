import NavBar from "./components/NavBar.tsx";
import { Routes, Route, Outlet } from "react-router-dom";
import {lazy, Suspense, useEffect} from 'react';
import AuthProvider from "./contexts/authContext.tsx";
import LoadingAnimation from "./components/LoadingAnimation.tsx";
import {Helmet} from "react-helmet";
import ProfileWrapper from "./components/userDetails/ProfileWrapper.tsx";
import useInterceptors from "./hooks/api/useInterceptors.ts";

const LoginView = lazy(() => import("./views/user/LogIn.tsx"));
const Register = lazy(() => import("./views/user/Register.tsx"));
const ForgotPassword = lazy(() => import("./views/user/ForgotPassword.tsx"));
const UserHome = lazy(() => import("./views/userAssets/UserAssets.tsx"));
const Locations = lazy(() => import("./views/Locations.tsx"));
const ViewAssetInstance = lazy(() => import("./views/asset/ViewAssetInstance.tsx"));
const UserAssetInstance = lazy(() => import("./views/userAssets/UserAssetInstance.tsx"));
const ReviewLender = lazy(() => import("./views/reviews/ReviewLender.tsx"));
const ReviewBorrower = lazy(() => import("./views/reviews/ReviewBorrower.tsx"));
const RequireAuth = lazy(() => import("./components/RequireAuth.tsx"));
const AddAsset = lazy(() => import("./views/addAsset.tsx"));
const ChangePassword = lazy(() => import("./views/user/ChangePassword.tsx"));
const ReviewsAssetInstance = lazy(() => import("./views/asset/ReviewsAssetInstance.tsx"));
const DiscoveryView = lazy(() => import("./views/Discovery.tsx"));
const NotFound = lazy(() => import("./views/NotFound.tsx"));
const Landing = lazy(() => import("./views/Landing.tsx"));


export default function App() {
    useInterceptors()

    return (
        <>
            <Helmet>
                <title>Lend a Read</title>
            </Helmet>
                <Suspense fallback={<LoadingAnimation />}>
                    <Routes>
                        <Route path="/" element={<Layout />}>
                            <Route index element={<Landing />} />
                            <Route path="login" element={<LoginView redirect={true}/>} />
                            <Route path="locations" element={<RequireAuth > <Locations /> </RequireAuth>} />
                            <Route path="userAssets" element={<RequireAuth> <UserHome /> </RequireAuth>} />
                            <Route path="forgotpassword" element={<ForgotPassword />} />
                            <Route path="changepassword" element={<ChangePassword />} />
                            <Route path="register" element={<Register />} />
                            <Route path="discovery" element={<DiscoveryView/>} />
                            <Route path="user/:id?" element={ <ProfileWrapper />} />
                            <Route path="book/:bookNumber" element={<ViewAssetInstance /> } />
                            <Route path="userBook/:id" element={<RequireAuth> <UserAssetInstance /> </RequireAuth>} />
                            <Route path="review/lender/:lendingNumber" element={<RequireAuth> <ReviewLender /> </RequireAuth>} />
                            <Route path="review/borrower/:lendingNumber" element={<RequireAuth> <ReviewBorrower /> </RequireAuth>} />
                            <Route path="addAssetView" element={<RequireAuth > <AddAsset /> </RequireAuth>} />
                            <Route path="book/:bookNumber/reviews" element={<ReviewsAssetInstance/>}/>
                            <Route path="notfound" element={<NotFound />} />
                            <Route path="*" element={<NotFound />} />
                        </Route>
                    </Routes>
                </Suspense>
        </>
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
