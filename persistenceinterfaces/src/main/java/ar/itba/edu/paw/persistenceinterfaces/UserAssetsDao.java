package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetLendingContext.Lending;

import java.util.Optional;

public interface UserAssetsDao {


    Optional<Lending> getBorrowedAsset(final int lendingId);

    Integer getActiveLendingsCount(final long assetInstanceId);
}
