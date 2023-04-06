package ar.itba.edu.paw.persistenceinterfaces;

import models.assetExistanceContext.implementations.AssetInstanceImpl;
import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetExistanceContext.interfaces.Book;

import java.util.List;
import java.util.Optional;

public interface BookInstanceDao {
    Optional<List<AssetInstanceImpl>> getAvailableBooks();

    Optional<Integer> addAssetInstance(final AssetInstance ai);
}
