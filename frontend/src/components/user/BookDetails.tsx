import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';

const BookDetails = ({book}) => {
    const { t } = useTranslation();

    return (
        <div className="book-details card p-5">
            <h5 className="card-title">{book.title}</h5>
            <div className="d-flex flex-row">
                <img src={book.imageUrl} />
                <div className="d-flex flex-col flex-1">
                    <p className="card-text">{t('by')} {book.author}</p>
                    <p className="card-text">{book.condition}</p>
                    <p className="card-text">{book.language}</p>
                    <p className="card-text">ISBN: {book.isbn}</p>
                </div>
            </div>
        </div>
    );
};

export default BookDetails;
