package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;

import java.util.Optional;

public interface ISBNCheckerService {
    Optional<BookImpl> getBookIfExistsByISBN(String isbn);
}
