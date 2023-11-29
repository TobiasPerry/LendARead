package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;

import java.util.List;

public interface AssetService {

    List<BookImpl> getBooks(final String isbn,final String author,final String title,final String language);

    BookImpl addBook(final String isbn,final String author,final String title,final String language) throws BookAlreadyExistException;

    BookImpl getBookByIsbn(final String isbn);

    BookImpl getBookById(final int id);
}
