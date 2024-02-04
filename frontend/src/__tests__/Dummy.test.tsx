import { describe, expect, it, vi } from 'vitest';
import { render, screen } from '@testing-library/react';
import React from 'react';
import Dummy from '../views/dummyView.tsx'; // Assuming the component path

describe('Dummy Component', () => {
    it('renders the text "does this pass tests??"', () => {
        render(<Dummy />);

        // Use Vitest's built-in assertion:
        const textElement = screen.getByText("does this pass tests??");
        expect(textElement).toBeInTheDocument();
    });
});
