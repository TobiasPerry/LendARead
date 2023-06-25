package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface AssetDao {
    Optional<List<BookImpl>> getAssets();

    BookImpl addAsset(final BookImpl bi) throws BookAlreadyExistException;

    Optional<BookImpl> getBook(final String isbn);

}
