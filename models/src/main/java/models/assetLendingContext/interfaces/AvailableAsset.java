package models.assetLendingContext.interfaces;

import models.userContext.interfaces.User;

public interface AvailableAsset {
    boolean canBorrow(User user);
}
