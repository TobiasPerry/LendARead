package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;

import java.util.Optional;

public interface UserAssetInstanceService {
     Optional<Lending> getBorrowedAssetInstance(final int lendingId) ;

}
