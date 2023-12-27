package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;

import java.util.List;

public interface AssetService {

    List<Asset> getBooks(final String isbn, final String author, final String title, final String language);

    Asset addBook(final String isbn, final String author, final String title, final String language) throws BookAlreadyExistException;

    Asset getBookByIsbn(final String isbn);

    Asset getBookById(final int id);
}
