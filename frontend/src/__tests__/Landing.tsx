// Landing.test.tsx
import React from 'react';
import { render, screen } from '@testing-library/react';
import Landing from '../views/Landing.tsx';

describe('Landing Component', () => {
    test('renders without crashing', () => {
        render(<Landing />);
        const titleElement = screen.getByText(/landing.hero.title/i);
        expect(titleElement).toBeInTheDocument();
    });

    test('displays the get started button', () => {
        render(<Landing />);
        const buttonElement = screen.getByText(/landing.hero.btn/i);
        expect(buttonElement).toBeInTheDocument();
    });
});
