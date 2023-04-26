package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AssetAvailabilityService {

    boolean borrowAsset(int assetId, String borrower, LocalDate devolutionDate);

    boolean setAssetPrivate(int assetId);

    boolean setAssetPublic(int assetId);

    List<BorrowedAssetInstance> getAllBorrowedAssetsInstances();

}
