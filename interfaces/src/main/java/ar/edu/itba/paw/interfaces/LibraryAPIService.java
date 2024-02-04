package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.Asset;

import java.io.IOException;

public interface LibraryAPIService {
     Asset getBookByISBN(final String isbn) throws IOException;
}
