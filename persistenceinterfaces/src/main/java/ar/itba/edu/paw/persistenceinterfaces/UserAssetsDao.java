package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;

import java.util.List;

public interface UserAssetsDao {

    List<BorrowedAssetInstance> getLendedAssets( );
}
