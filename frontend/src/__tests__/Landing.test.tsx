import React from "react";
import AuthContextProvider from "../contexts/authContext.tsx";
import { render, screen } from '@testing-library/react';
import Landing from "../views/Landing.tsx";
import {setupTests} from "../testUtils/setupTests.js.ts";


setupTests()
describe('Landing Component', () => {
    test('renders without crashing', () => {
        render(
            // <AuthContextProvider>
                <Landing />
            // </AuthContextProvider>
        );
        const titleElement = screen.getByText("La nueva era de los libros\n");
        expect(titleElement).toBeInTheDocument();
    });
});
