// Landing.test.tsx
import { render, screen } from '@testing-library/react';
import Landing from '../views/Landing.tsx';
import React from 'react'; // Add this line
import { I18nextProvider } from 'react-i18next';
import i18n from '../i18n.js'
import AuthContextProvider, {AuthContext} from "../contexts/authContext.tsx";

// Landing.test.tsx

// Mock localStorage and sessionStorage
const localStorageMock = (function () {
    let store = {};
    return {
        getItem(key) {
            return store[key] || null;
        },
        setItem(key, value) {
            store[key] = value.toString();
        },
        clear() {
            store = {};
        },
        removeItem(key) {
            delete store[key];
        },
    };
})();

const sessionStorageMock = (function () {
    let store = {};
    return {
        getItem(key) {
            return store[key] || null;
        },
        setItem(key, value) {
            store[key] = value.toString();
        },
        clear() {
            store = {};
        },
        removeItem(key) {
            delete store[key];
        },
    };
})();

Object.defineProperty(window, 'localStorage', {
    value: localStorageMock,
});

Object.defineProperty(window, 'sessionStorage', {
    value: sessionStorageMock,
});


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
            <AuthContextProvider>
            <Landing />
            </AuthContextProvider>

        );
        const buttonElement = screen.getByText(/landing.hero.btn/i);
        expect(buttonElement).toBeInTheDocument();
    });
});
