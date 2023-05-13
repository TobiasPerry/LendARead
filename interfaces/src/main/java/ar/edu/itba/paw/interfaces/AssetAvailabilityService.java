package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.*;

import java.time.LocalDate;

public interface AssetAvailabilityService {

    void borrowAsset(int assetId, String borrower, LocalDate devolutionDate)  throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException;

    void setAssetPrivate(int assetId) throws AssetInstanceNotFoundException;

    void setAssetPublic(int assetId) throws AssetInstanceNotFoundException;

    void returnAsset(int assetId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException;

    void confirmAsset(int assetId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException;
}
