package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;

import java.util.List;
import java.util.Optional;

public interface AssetDao {
    Optional<List<Asset>> getAssets();

    Asset addAsset(final Asset bi) throws BookAlreadyExistException;

    Optional<Asset> getBookByIsbn(final String isbn);

    List<Asset> getBooks(final String isbn, final String author, final String title, final String language);

    Asset getBookById(final int id);

}
