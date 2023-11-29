package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface AssetDao {
    Optional<List<BookImpl>> getAssets();

    BookImpl addAsset(final BookImpl bi) throws BookAlreadyExistException;

    Optional<BookImpl> getBookByIsbn(final String isbn);

    List<BookImpl> getBooks(final String isbn,final String author,final String title,final String language);

    BookImpl getBookById(final int id);

}
