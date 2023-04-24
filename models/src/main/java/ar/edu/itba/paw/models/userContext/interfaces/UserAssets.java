package ar.edu.itba.paw.models.userContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

import java.util.List;

public interface UserAssets {

    List<AssetInstance> getLendedBooks();
    List<AssetInstance> getBorrowedBooks();
    List<AssetInstance> getMyBooks();
}
