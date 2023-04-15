package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.Date;

public interface LendingsService {
    boolean lendBook(int assetId,User borrower,Date devolutionDate);
}
