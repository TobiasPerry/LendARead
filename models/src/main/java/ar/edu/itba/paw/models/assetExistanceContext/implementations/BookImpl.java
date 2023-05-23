package ar.edu.itba.paw.models.assetExistanceContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;

import javax.persistence.*;
@Entity
@Table(name = "book")
public class BookImpl implements Book {

    @Column(length = 100, nullable = false, unique = true)
    private  String isbn;

    @Column(length = 100, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private  String title;

    @Column(length = 100, nullable = false, name = "lang")
    private String language;

    @Column(length = 100, nullable = false)
    private  String type = "Book";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_uid_seq")
    @SequenceGenerator(sequenceName = "books_uid_seq", name = "books_uid_seq", allocationSize = 1)
    @Column(name = "uid")
    private  int id;

    public BookImpl(final int id,final String isbn,final String author,final String title,final String language) {
        this.isbn = convertToISBN13(isbn);
        this.author = author;
        this.title = title;
        this.language = language;
        this.id = id;
    }

    public BookImpl() {

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
    public int getId() {
        return this.id;
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
