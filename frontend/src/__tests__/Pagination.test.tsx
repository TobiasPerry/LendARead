import { describe, expect, it, vi } from 'vitest';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Pagination from '../components/Pagination.tsx';

describe('Pagination Component', () => {
    it('renders navigation when totalPages is greater than 1', () => {
        render(<Pagination totalPages={3} currentPage={1} changePage={vi.fn()} />);

        const nav = screen.getByRole('navigation');
        expect(nav).toBeInTheDocument();
    });

    it('does not render navigation when totalPages is 1', () => {
        render(<Pagination totalPages={1} currentPage={1} changePage={vi.fn()} />);

        const nav = screen.queryByRole('navigation');
        expect(nav).not.toBeInTheDocument();
    });

    it('renders previous and next buttons', () => {
        render(<Pagination totalPages={3} currentPage={2} changePage={vi.fn()} />);

        const previousButton = screen.getByRole('button', { name: /previous/i });
        const nextButton = screen.getByRole('button', { name: /next/i });

        expect(previousButton).toBeInTheDocument();
        expect(nextButton).toBeInTheDocument();
    });

    it('disables the previous button when on the first page', () => {
        render(<Pagination totalPages={3} currentPage={1} changePage={vi.fn()} />);

        const nextButton = screen.getByTestId('next-btn');
        const previousButton = screen.getByTestId('previous-btn');

        expect(previousButton).toHaveClass('disabled');
        expect(nextButton).not.toHaveClass('disabled');
    });

    it('disables the next button when on the last page', () => {
        render(<Pagination totalPages={3} currentPage={3} changePage={vi.fn()} />);

        const nextButton = screen.getByTestId('next-btn');
        const previousButton = screen.getByTestId('previous-btn');

        expect(previousButton).not.toHaveClass('disabled');
        expect(nextButton).toHaveClass('disabled');
    });

    it('calls changePage with the correct page number when buttons are clicked', async () => {
        const changePage = vi.fn();
        render(<Pagination totalPages={3} currentPage={2} changePage={changePage} />);

        const previousButton = screen.getByRole('button', { name: /previous/i });
        const nextButton = screen.getByRole('button', { name: /next/i });

        await userEvent.click(previousButton);
        expect(changePage).toHaveBeenCalledWith(1);

        await userEvent.click(nextButton);
        expect(changePage).toHaveBeenCalledWith(3);
    });

    it('displays the current page and total pages', () => {
        render(<Pagination totalPages={5} currentPage={3} changePage={vi.fn()} />);

        const currentPageInfo = screen.getByText('3 / 5');
        expect(currentPageInfo).toBeInTheDocument();
    });
});
