package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.time.LocalDate;
import java.util.List;

public interface LendingService {

    Lending borrowAsset(final int assetId, final String borrower, final LocalDate borrowDate, final LocalDate devolutionDate) throws AssetInstanceBorrowException, UserNotFoundException, DayOutOfRangeException, MaxLendingDaysException, AssetIsNotAvailableException, AssetInstanceIsNotReservableException;

    void returnAsset(final int lendingId) throws AssetInstanceNotFoundException, InvalidLendingStateTransitionException, UserNotFoundException, LendingNotFoundException;

    void confirmAsset(final int lendingId) throws AssetInstanceNotFoundException, InvalidLendingStateTransitionException, UserNotFoundException, LendingNotFoundException;

    User getLender(final int lendingId) throws LendingNotFoundException;

    void rejectAsset(final int lendingId) throws AssetInstanceNotFoundException, InvalidLendingStateTransitionException, UserNotFoundException, LendingNotFoundException;

    void changeLending(final int lendingId,final String state) throws  InvalidLendingStateTransitionException, UserNotFoundException, LendingNotFoundException;
    PagingImpl<Lending> getPagingActiveLendings(final int page, final int size, final Integer aiId, final Integer borrowerId, final List<String> lendingState, final Integer lenderId, final String sort, final String sortDirection, final LocalDate startingBefore, final LocalDate startingAfter,final LocalDate endBefore,final LocalDate endAfter);

    User getBorrower(final int lendingId) throws  LendingNotFoundException;

     void cancelAsset(final int lendingId) throws AssetInstanceNotFoundException, InvalidLendingStateTransitionException, UserNotFoundException, LendingNotFoundException;

    void notifyNewLendings();
}
