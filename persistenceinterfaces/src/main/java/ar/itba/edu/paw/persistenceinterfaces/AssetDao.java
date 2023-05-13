package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface AssetDao {
    Optional<List<Book>> getAssets();

    Book addAsset(final Book bi) throws BookAlreadyExistException;

    Optional<Book> getBook(final String isbn);

    boolean deleteAsset(final int id);
}
