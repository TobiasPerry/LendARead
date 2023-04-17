package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;

public class BookImpl implements Book {


    private final String isbn;

    private String author;

    private final String title;

    private String language;


    private final String type = "Book";

    public BookImpl(String isbn, String author, String title, String language) {
        this.isbn = convertToISBN13(isbn);
        this.author = author;
        this.title = title;
        this.language = language;
    }

    private static String convertToISBN13(String isbn) {
        // Eliminar guiones y espacios en blanco del ISBN
        isbn = isbn.replace("-", "").replace(" ", "");

        // Verificar si el ISBN es de longitud 10
        if (isbn.length() == 10) {
            // Calcular el dígito de control del ISBN-13
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(isbn.charAt(i));
                sum += (i % 2 == 0) ? digit : digit * 3;
            }
            int checkDigit = (10 - (sum % 10)) % 10;

            // Construir el ISBN-13 completo
            isbn = "978" + isbn.substring(0, 9) + checkDigit;
        }
        return isbn;
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
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void setLanguage(String language) {
        this.language = language;
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
