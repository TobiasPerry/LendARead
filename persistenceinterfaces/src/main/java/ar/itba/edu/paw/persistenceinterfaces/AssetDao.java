package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;

import java.util.List;
import java.util.Optional;

public interface AssetDao {
    Optional<List<Book>> getAssets();

    Optional<Integer> addAsset(final Book bi);

    Optional<Book> getBook(final String isbn);

    boolean deleteAsset(final int id);
}
