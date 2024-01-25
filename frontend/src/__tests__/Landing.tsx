// Landing.test.tsx
import React from 'react';
import { render, screen } from '@testing-library/react';
import Landing from '../views/Landing.tsx';
import { I18nextProvider } from 'react-i18next';
import i18n from '../i18n.js'


describe('Landing Component', () => {
    test('renders without crashing', () => {
        render(
            <I18nextProvider i18n={i18n}>
                <Landing />
            </I18nextProvider>
        );
        const titleElement = screen.getByText(/landing.hero.title/i);
        expect(titleElement).toBeInTheDocument();
    });

    test('displays the get started button', () => {
        render(    <I18nextProvider i18n={i18n}>
            <Landing />
        </I18nextProvider>);
        const buttonElement = screen.getByText(/landing.hero.btn/i);
        expect(buttonElement).toBeInTheDocument();
    });
});
