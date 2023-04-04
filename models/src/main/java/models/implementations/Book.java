package models.implementations;

import models.Interfaces.Asset;

public class Book implements Asset {

    private final int uid;

    private final String isbn;

    private final String autor;

    private final String title;

    private final String lenguaje;

    private final byte[] photo;

    private final String type = "Book";

    public Book(int uid, String isbn, String autor, String title, String lenguaje, byte[] photo) {
        this.uid = uid;
        this.isbn = isbn;
        this.autor = autor;
        this.title = title;
        this.lenguaje = lenguaje;
        this.photo = photo;
    }

    @Override
    public int getId() {
        return this.uid;
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
}
