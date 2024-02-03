import React from "react";
import AuthContextProvider from "../contexts/authContext.tsx";
import { render, screen } from '@testing-library/react';
import Landing from "../views/Landing.tsx";
import {setupTests} from "../testUtils/setupTests.js.ts";
import {BrowserRouter} from "react-router-dom";


setupTests()
describe('Landing Component', () => {
    test('renders without crashing', () => {
        render(
            // <AuthContextProvider>
            <BrowserRouter>
                <Landing />
            </BrowserRouter>
            // </AuthContextProvider>
        );
        const titleElement = screen.getByText("landing.hero.title");
        expect(titleElement).toBeInTheDocument();
    });
});
