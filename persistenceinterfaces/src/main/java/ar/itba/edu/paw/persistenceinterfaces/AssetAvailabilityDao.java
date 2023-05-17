package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;

import java.time.LocalDate;

public interface AssetAvailabilityDao {

    int borrowAssetInstance(int assetInstanceId, int userId, LocalDate borrowDate,LocalDate devolutionDate,LendingState lendingState);

    boolean changeLendingStatus(int assetInstanceId, LendingState lendingState);
}
