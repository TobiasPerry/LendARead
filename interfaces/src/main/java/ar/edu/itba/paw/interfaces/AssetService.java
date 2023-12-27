package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;

public interface AssetService {

    PagingImpl<Asset> getBooks(final int page,final int itemsPerPage,final String isbn, final String author, final String title, final String language);

    Asset addBook(final String isbn, final String author, final String title, final String language) throws BookAlreadyExistException;

    Asset getBookByIsbn(final String isbn);

    Asset getBookById(final Long id);
}
