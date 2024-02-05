import { render, screen } from '@testing-library/react';
import NavBar from '../components/NavBar.tsx';
import React from 'react';
import {expect, it, vi} from 'vitest';
import {AuthContext} from "../contexts/authContext.tsx";
import {BrowserRouter} from "react-router-dom";



describe('Test Navbar component on log in state ', () => {
    it('Not show data when log out', () => {
        render(
            <BrowserRouter>
                <AuthContext.Provider value={
                    {
                        isLoggedIn: false,
                        logout: vi.fn(),
                        login: vi.fn(),
                        handleChangePassword: vi.fn(),
                        handleForgotPassword: vi.fn(),
                        uploadUserImage: vi.fn(),
                        changeUserRole: vi.fn(),
                        user: "",
                        userDetails: {
                            email: "",
                            image: "",
                            rating: 0,
                            ratingAsBorrower: 0,
                            ratingAsLender: 0,
                            role: "",
                            selfUrl: "",
                            telephone: "",
                            userName: "",
                        },
                        userImage: "",
                        smallUserImage: "",
                    }
                }>
                    <NavBar/>
                </AuthContext.Provider>
            </BrowserRouter>
        )

        const logInBtn = screen.getByTestId('login-btn')
        const logOutBtn = screen.queryByTestId('logout-btn');
        const profileBtn = screen.queryByTestId('profile-btn');
        const userImg = screen.queryByTestId('user-img');

        expect(logInBtn).toBeInTheDocument()
        expect(logOutBtn).toBeNull()
        expect(profileBtn).toBeNull()
        expect(userImg).toBeNull()

    })

    it('Show data when log out', () => {
        render(
            <BrowserRouter>
                <AuthContext.Provider value={
                    {
                        isLoggedIn: true,
                        logout: vi.fn(),
                        login: vi.fn(),
                        handleChangePassword: vi.fn(),
                        handleForgotPassword: vi.fn(),
                        uploadUserImage: vi.fn(),
                        changeUserRole: vi.fn(),
                        user: "",
                        userDetails: {
                            email: "",
                            image: "",
                            rating: 0,
                            ratingAsBorrower: 0,
                            ratingAsLender: 0,
                            role: "",
                            selfUrl: "",
                            telephone: "",
                            userName: "",
                        },
                        userImage: "",
                        smallUserImage: "",
                    }
                }>
                    <NavBar/>
                </AuthContext.Provider>
            </BrowserRouter>
        )


        const logOutBtn = screen.getByTestId('logout-btn')
        const userImg = screen.getByTestId('user-img')
        const profileBtn = screen.getByTestId('profile-btn')

        const logInBtn = screen.queryByTestId('login-btn');

        expect(logInBtn).toBeNull();
        expect(logOutBtn).toBeInTheDocument()
        expect(userImg).toBeInTheDocument()
        expect(profileBtn).toBeInTheDocument()
    })

    it(' show the user image', () => {
        render(
            <BrowserRouter>
                <AuthContext.Provider value={
                    {
                        isLoggedIn: true,
                        logout: vi.fn(),
                        login: vi.fn(),
                        handleChangePassword: vi.fn(),
                        handleForgotPassword: vi.fn(),
                        uploadUserImage: vi.fn(),
                        changeUserRole: vi.fn(),
                        user: "",
                        userDetails: {
                            email: "",
                            image: "",
                            rating: 0,
                            ratingAsBorrower: 0,
                            ratingAsLender: 0,
                            role: "",
                            selfUrl: "",
                            telephone: "",
                            userName: "",
                        },
                        userImage: "lendaread.com/api/image/12",
                        smallUserImage: "lendaread.com/api/image/12",
                    }
                }>
                    <NavBar/>
                </AuthContext.Provider>
            </BrowserRouter>
        )

        const userImg = screen.getByTestId('user-img')

        // expect(userImg.src).toBe("lendaread.com/api/image/12")
        expect(userImg).toHaveAttribute('src', 'lendaread.com/api/image/12')

    });


})
