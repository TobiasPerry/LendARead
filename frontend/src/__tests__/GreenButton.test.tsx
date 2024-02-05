import { render, screen } from '@testing-library/react';
import GreenButton from '../components/GreenButton.tsx';
import userEvent from '@testing-library/user-event';
import { it, vi } from 'vitest';

describe('Test button component ', () => {
    it('renders the provided text on the button', () => {
        const buttonText = 'Click me!';
        render(<GreenButton action={() => {
        }} text={buttonText}/>);

        const button = screen.getByRole('button', {name: buttonText});
        expect(button).toBeInTheDocument();
        expect(button).toHaveTextContent(buttonText);
    });

    it('calls the provided action function when clicked', async () => {
        const mockAction = vi.fn(); // Mock the action function
        const buttonText = 'Click me!';

        render(<GreenButton action={mockAction} text="Click me!"/>);

        const button = screen.getByRole('button', {name: buttonText});
        await userEvent.click(button);

        expect(mockAction).toHaveBeenCalledTimes(1);
    });
})