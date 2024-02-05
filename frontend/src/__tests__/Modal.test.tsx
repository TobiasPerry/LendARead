import { describe, expect, it, vi } from 'vitest';
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import Modal from '../components/modals/Modal.tsx'

describe('Modal Component', () => {
    it('Renders the modal when showModal is true', async () => {
        render(<Modal showModal={true} title="Test Modal" subtitle="This is a test modal" btnText="Close" handleCloseModal={() => {}}/>);

        // Wait for the modal to be fully rendered
        await waitFor(() => {
            const modal = screen.getByRole('dialog');
            expect(modal).toBeInTheDocument();
            expect(modal).toHaveClass('show')
        });

        // Check for expected content
        const modalTitle = screen.getByText('Test Modal');
        const modalSubtitle = screen.getByText('This is a test modal');

        expect(modalTitle).toBeInTheDocument();
        expect(modalSubtitle).toBeInTheDocument();
    });

    it('Not renders the modal when showModal is true', async () => {
        render(<Modal showModal={false} title="Test Modal" subtitle="This is a test modal" btnText="Close" handleCloseModal={() => {}}/>);

        // Wait for the modal to be fully rendered
        await waitFor(() => {
            const modal = screen.getByRole('dialog');
            expect(modal).toBeInTheDocument();
            expect(modal).not.toHaveClass('show')
        });

        // Check for expected content
        const modalTitle = screen.getByText('Test Modal');
        const modalSubtitle = screen.getByText('This is a test modal');

        expect(modalTitle).toBeInTheDocument();
        expect(modalSubtitle).toBeInTheDocument();
    });


});
