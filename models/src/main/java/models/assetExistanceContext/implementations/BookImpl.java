package models.assetExistanceContext.implementations;

import models.assetExistanceContext.interfaces.Book;

public class BookImpl implements Book {


    private final String isbn;

    private final String author;

    private final String title;

    private final String language;

    private final byte[] photo;

    private final String type = "Book";

    public BookImpl( String isbn, String author, String title, String language, byte[] photo) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.language = language;
        this.photo = photo;
    }



    @Override
    public String getName() {
        return this.title;
    }

    @Override
    public byte[] getPhoto() {
        return this.photo;
    }

    @Override
    public String display() {
        return null;
    }

    @Override
    public String type() {
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
    public String author() {
        return this.author;
    }
}
