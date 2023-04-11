package ar.edu.itba.paw.models.assetExistanceContext.factories;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;

final public class BookFactory {
    public static Book createBook(String isbn, String author, String title, String language, byte[] photo) {
        return new BookImpl(isbn, author, title, language);
    }
}

