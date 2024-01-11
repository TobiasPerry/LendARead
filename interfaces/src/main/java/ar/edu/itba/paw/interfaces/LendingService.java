package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.time.LocalDate;

public interface LendingService {

    Lending borrowAsset(final int assetId, final String borrower, final LocalDate borrowDate, final LocalDate devolutionDate)  throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException;

    void returnAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException, UserNotFoundException;

    void confirmAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException, UserNotFoundException;

    User getLender(final int lendingId) throws LendingNotFoundException;

    void rejectAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException, UserNotFoundException;

    void changeLending(final int lendingId,final String state) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException, UserNotFoundException;
    PagingImpl<Lending> getPagingActiveLendings(final int page, final int size, final Integer aiId, final Integer borrowerId, final LendingState lendingState, final Integer lenderId, final String sort, final String sortDirection);

    User getBorrower(final int lendingId) throws  LendingNotFoundException;

     void cancelAsset(final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException, UserNotFoundException;

    void notifyNewLendings();
}
