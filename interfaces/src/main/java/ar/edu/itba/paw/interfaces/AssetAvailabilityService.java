package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.*;

import java.time.LocalDate;

public interface AssetAvailabilityService {

    void borrowAsset(final int assetId, final String borrower, final LocalDate devolutionDate)  throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException;

    void setAssetPrivate(final int assetId) throws AssetInstanceNotFoundException;

    void setAssetPublic(final int assetId) throws AssetInstanceNotFoundException;

    void returnAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException;

    void confirmAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException;
    void rejectAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException;
}
