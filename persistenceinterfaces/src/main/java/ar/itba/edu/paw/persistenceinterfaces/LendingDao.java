package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.interfaces.AvailableAsset;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.Date;

public interface LendingDao {

    boolean createLending(AssetInstance ai, User us, Date devolutionDate);
}
