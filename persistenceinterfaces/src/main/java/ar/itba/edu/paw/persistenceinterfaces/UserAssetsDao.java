package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;

import java.util.List;

public interface UserAssetsDao {

    List<BorrowedAssetInstance> getLendedAssets(String email);

    List<BorrowedAssetInstance> getBorrowedAssets(String email);
    List<AssetInstance> getUsersAssets(String email);

}
