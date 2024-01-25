// Landing.test.tsx
import { render, screen } from '@testing-library/react';
import Landing from '../views/Landing.tsx';
import React from 'react'; // Add this line
import { I18nextProvider } from 'react-i18next';
import i18n from '../i18n.js'
import AuthContextProvider, {AuthContext} from "../contexts/authContext.tsx";


describe('Landing Component', () => {
    test('renders without crashing', () => {
        render(
            <AuthContextProvider>
                <Landing />
            </AuthContextProvider>
        );
        const titleElement = screen.getByText(/landing.hero.title/i);
        expect(titleElement).toBeInTheDocument();
    });

    test('displays the get started button', () => {
        render(
            <Landing />
       );
        const buttonElement = screen.getByText(/landing.hero.btn/i);
        expect(buttonElement).toBeInTheDocument();
    });
});
