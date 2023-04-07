package ar.itba.edu.paw.persistenceinterfaces;

import models.assetExistanceContext.implementations.AssetInstanceImpl;
import models.assetExistanceContext.interfaces.Book;

import java.util.List;
import java.util.Optional;

public interface AssetDao {
    Optional<List<AssetInstanceImpl>> getAssets();

    Optional<Integer> addAsset(final Book ai);
}
