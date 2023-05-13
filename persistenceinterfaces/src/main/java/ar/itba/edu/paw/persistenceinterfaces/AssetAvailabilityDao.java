package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;

import java.time.LocalDate;

public interface AssetAvailabilityDao {

    boolean borrowAssetInstance(int assetInstanceId, int userId, LocalDate borrowDate,LocalDate devolutionDate);

    boolean changeLendingStatus(int assetInstanceId, LendingState lendingState);
}
