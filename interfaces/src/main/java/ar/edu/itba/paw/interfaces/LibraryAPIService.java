package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;

import java.io.IOException;

public interface LibraryAPIService {
    public Book getBookByISBN(final String isbn) throws IOException;
}
