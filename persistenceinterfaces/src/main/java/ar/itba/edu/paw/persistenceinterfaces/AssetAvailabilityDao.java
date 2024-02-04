package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.assetLendingContext.LendingState;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssetAvailabilityDao {

    Lending borrowAssetInstance(AssetInstance assetInstance, User user, LocalDate borrowDate, LocalDate devolutionDate, LendingState lendingState);

    List<Lending> getActiveLendings(AssetInstance ai);
    void changeLendingStatus(Lending lending, final LendingState lendingState);

    PagingImpl<Lending> getPagingActiveLending(final int pageNum, final int itemsPerPage, final Integer aiId, final Integer borrowerId, final List<String> lendingState, final Integer lenderId, final String sort, final String sortDirection, final LocalDate startingBefore, final LocalDate startingAfter, final LocalDate endBefore, final LocalDate endAfter) ;
    Optional<List<Lending>> getActiveLendingsStartingOn(LocalDate date);

    Optional<List<Lending>> getActiveLendingEndingOn(LocalDate date);

    Optional<Lending> getLendingById(final int lendingId);
}
