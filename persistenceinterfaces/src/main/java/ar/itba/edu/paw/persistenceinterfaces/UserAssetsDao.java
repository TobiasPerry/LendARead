package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;

import java.util.List;

public interface UserAssetsDao {

    List<BorrowedAssetInstance> getLendedAssets(final String email, final String filterValue, final String filterAtribuite, final String sortAtribuite, final String direction);

    List<BorrowedAssetInstance> getBorrowedAssets(final String email, final String filterValue, final String filterAtribuite, final String sortAtribuite, final String direction);
    List<AssetInstance> getUsersAssets(final String email, final String filterValue, final String filterAtribuite, final String sortAtribuite, final String direction);
    List<BorrowedAssetInstance> getLendedAssetsFilteredBy(String email, String Attribuite);
}
