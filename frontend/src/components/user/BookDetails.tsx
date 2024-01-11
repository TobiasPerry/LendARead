import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

const BookDetails = () => {
    const { t } = useTranslation();
    const [book, setBook] = useState(null);

    useEffect(() => {
        // Simulate fetching book details
        const fetchBookDetails = async () => {
            // Replace with actual fetch call
            const bookDetails = {
                title: 'Thinking, Fast and Slow',
                author: 'Daniel Kahneman',
                condition: t('conditionAcceptable'),
                language: t('languageEnglish'),
                isbn: '9780141033570'
            };
            setBook(bookDetails);
        };

        fetchBookDetails();
    }, [t]);

    if (!book) {
        return <div>Loading...</div>;
    }

    return (
        <div className="book-details card p-5">
            <h5 className="card-title">{book.title}</h5>
            <p className="card-text">{t('by')} {book.author}</p>
            <p className="card-text">{book.condition}</p>
            <p className="card-text">{t('language')}: {book.language}</p>
            <p className="card-text">ISBN: {book.isbn}</p>
        </div>
    );
};

export default BookDetails;
