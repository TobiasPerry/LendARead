package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;

import java.util.Optional;

public interface ISBNCheckerService {
    Optional<Asset> getBookIfExistsByISBN(String isbn);
}
