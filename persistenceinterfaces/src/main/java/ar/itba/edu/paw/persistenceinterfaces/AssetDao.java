package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;

import java.util.Optional;

public interface AssetDao {

    Asset addAsset(final Asset bi) throws BookAlreadyExistException;

    Optional<Asset> getBookByIsbn(final String isbn);

    PagingImpl<Asset> getBooks(final int page,final int itemsPerPage,final String isbn, final String author, final String title, final String language);

    Asset getBookById(final Long id);

}
