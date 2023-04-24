package ar.itba.edu.paw.persistenceinterfaces;

import java.time.LocalDate;

public interface AssetAvailabilityDao {

    boolean borrowAssetInstance(int assetInstanceId, int userId, LocalDate borrowDate,LocalDate devolutionDate);

}
