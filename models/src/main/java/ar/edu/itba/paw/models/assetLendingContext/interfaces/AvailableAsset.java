package ar.edu.itba.paw.models.assetLendingContext.interfaces;

import ar.edu.itba.paw.models.userContext.interfaces.User;

public interface AvailableAsset {
    boolean canBorrow(User user);
}
