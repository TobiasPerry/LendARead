package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;

public class BookImpl implements Book {


    private final String isbn;

    private final String author;

    private final String title;

    private final String language;


    private final String type = "Book";

    public BookImpl(String isbn, String author, String title, String language) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.language = language;
    }



    @Override
    public String getName() {
        return this.title;
    }


    @Override
    public String display() {
        return null;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getIsbn() {
        return this.isbn;
    }

    @Override
    public String getLanguage() {
        return this.language;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public String toString() {
        return "BookImpl{" +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
