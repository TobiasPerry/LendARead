package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;

import java.util.Optional;

public interface UserAssetsDao {


    Optional<Lending> getBorrowedAsset(final int lendingId);
}
