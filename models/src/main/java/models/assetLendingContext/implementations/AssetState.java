package models.assetLendingContext.implementations;

import models.userContext.interfaces.User;

public enum AssetState {
    PUBLIC() {

    },
    PRIVATE() {

    },
    BORROWED() {

    },
    FROZEN() {

    };

    boolean canBorrow() { return false;}
}
