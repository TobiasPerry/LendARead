package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.time.LocalDate;
import java.util.List;

public interface AssetAvailabilityDao {

    LendingImpl borrowAssetInstance(AssetInstanceImpl assetInstance, UserImpl user, LocalDate borrowDate, LocalDate devolutionDate, LendingState lendingState);

    List<LendingImpl> getActiveLendings(AssetInstanceImpl ai);
    void changeLendingStatus(LendingImpl lending,final LendingState lendingState);

    public PagingImpl<LendingImpl> getPagingActiveLending(final AssetInstanceImpl ai, final int pageNum, final int itemsPerPage);
}
