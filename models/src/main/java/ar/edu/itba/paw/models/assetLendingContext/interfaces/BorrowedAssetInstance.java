package ar.edu.itba.paw.models.assetLendingContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LoanState;

public interface BorrowedAssetInstance extends AssetInstance {

    String getDueDate();

    String getBorrower();

}

