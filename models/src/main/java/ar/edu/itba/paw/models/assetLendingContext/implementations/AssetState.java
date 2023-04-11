package ar.edu.itba.paw.models.assetLendingContext.implementations;

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
