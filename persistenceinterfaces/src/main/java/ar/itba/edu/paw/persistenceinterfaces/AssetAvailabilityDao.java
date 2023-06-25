package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AssetAvailabilityDao {

    LendingImpl borrowAssetInstance(AssetInstanceImpl assetInstance, UserImpl user, LocalDate borrowDate, LocalDate devolutionDate, LendingState lendingState);

    void changeLendingStatus(LendingImpl lending, final LendingState lendingState);

    Optional<List<LendingImpl>> getActiveLendingsStartingOn(LocalDate date);

    Optional<List<LendingImpl>> getActiveLendingEndingOn(LocalDate date);
}
