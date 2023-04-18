package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;

import java.util.Optional;

public interface ISBNCheckerService {
    Optional<Book> getBookIfExistsByISBN(String isbn);
}
