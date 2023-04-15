package ar.edu.itba.paw.models.assetExistanceContext.factories;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;

final public class BookFactory {
    public static Book createBook(String isbn) {
        return new BookImpl(isbn, "none", "node", "none");
    }
}

