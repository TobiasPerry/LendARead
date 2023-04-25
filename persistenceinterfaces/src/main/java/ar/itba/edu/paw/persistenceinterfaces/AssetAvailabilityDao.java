package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetLendingContext.interfaces.LendingDetails;

import java.time.LocalDate;
import java.util.List;

public interface AssetAvailabilityDao {

    boolean borrowAssetInstance(int assetInstanceId, int userId, LocalDate borrowDate,LocalDate devolutionDate);

    List<LendingDetails> getAllLendings();
}
