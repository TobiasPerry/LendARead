package ar.edu.itba.paw.models.assetLendingContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

public interface BorrowedAssetInstance extends AssetInstance {

    String getDueDate();

    String getBorrower();
}

