package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;

import java.io.IOException;

public interface LibraryAPIService {
     BookImpl getBookByISBN(final String isbn) throws IOException;
}
