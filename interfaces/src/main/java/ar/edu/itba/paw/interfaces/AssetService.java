package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetAlreadyExistException;
import ar.edu.itba.paw.exceptions.AssetNotFoundException;
import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToCreateAssetException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

public interface AssetService {

    PagingImpl<Asset> getBooks(final int page,final int itemsPerPage,final String isbn, final String author, final String title, final String language);
    Asset addBook(final String isbn, final String author, final String title, final String language) throws AssetAlreadyExistException, UnableToCreateAssetException;
    Asset getBookById(final Long id) throws AssetNotFoundException;
    void updateBook(final Long id, final String isbn, final String author, final String title, final String language) throws AssetNotFoundException, LanguageNotFoundException, AssetAlreadyExistException;
}
