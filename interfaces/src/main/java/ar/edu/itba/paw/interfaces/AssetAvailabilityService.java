package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.BorrowedAssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AssetAvailabilityService {

    void borrowAsset(int assetId, String borrower, LocalDate devolutionDate)  throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException;

    void setAssetPrivate(int assetId) throws AssetInstanceNotFoundException;

    void setAssetPublic(int assetId) throws AssetInstanceNotFoundException;

    void returnAsset(int assetId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessful;
}
