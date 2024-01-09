package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;

public interface UserAssetInstanceService {
     Lending getBorrowedAssetInstance(final int lendingId) throws LendingNotFoundException;

}
