package ar.edu.itba.paw.models.assetExistanceContext.factories;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;

final public class BookFactory {
    public static BookImpl createBook(int id,String isbn,String author,String title,String language) {
        return new BookImpl(id,isbn, author, title, language);
    }
    public static BookImpl createBook(String isbn,String author,String title,String language) {
        return new BookImpl(isbn, author, title, language);
    }
}

