package ar.edu.itba.paw.models.userContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;

import java.util.List;

public interface UserAssets {

    UserAssets filter(String table, String attribuite);

    UserAssets sort(String table, String attribuite, String direction);

    List<BorrowedAssetInstance> getLendedBooks();
    List<BorrowedAssetInstance> getBorrowedBooks();
    List<AssetInstance> getMyBooks();
}
