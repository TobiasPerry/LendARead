package ar.edu.itba.paw.models.assetExistanceContext;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Asset {

    @Column(length = 100, nullable = false, unique = true)
    private  String isbn;

    @Column(length = 100, nullable = false)
    private String author;

    @Column(length = 100, nullable = false)
    private  String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lang", referencedColumnName = "id", nullable = false)
    private Language language;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_uid_seq")
    @SequenceGenerator(sequenceName = "book_uid_seq", name = "book_uid_seq", allocationSize = 1)
    @Column(name = "uid")
    private  Long id;

    public Asset(final Long id, final String isbn, final String author, final String title, final Language language) {
        this.isbn = convertToISBN13(isbn);
        this.author = author;
        this.title = title;
        this.language = language;
        this.id = id;
    }
    public Asset(final String isbn, final String author, final String title, final Language language) {
        this.isbn = convertToISBN13(isbn);
        this.author = author;
        this.title = title;
        this.language = language;
    }


    public Asset() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Asset book = (Asset) o;

        if (id != book.id) return false;
        if (!Objects.equals(isbn, book.isbn)) return false;
        if (!Objects.equals(author, book.author)) return false;
        if (!Objects.equals(title, book.title)) return false;
        return Objects.equals(language, book.language);
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = (int) (31 * result + id);
        return result;
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

    public String getName() {
        return this.title;
    }


    public String display() {
        return null;
    }



    public String getIsbn() {
        return this.isbn;
    }

    public Language getLanguage() {
        return this.language;
    }

    public String getAuthor() {
        return this.author;
    }

    public Long getId() {
        return this.id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setIsbn(String isbn) {
        this.isbn = convertToISBN13(isbn);
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String toString() {
        return "BookImpl{" +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
