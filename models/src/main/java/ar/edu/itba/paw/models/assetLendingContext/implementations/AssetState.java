package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;

public enum AssetState {
    PUBLIC() {
        @Override
        boolean canBorrow() {
            return true;
        }
    },
    PRIVATE() {
        @Override
        boolean canBorrow() {
            return false;
        }
    },
    BORROWED() {
        @Override
        boolean canBorrow() {
            return false;
        }
    },
    FROZEN() {
        @Override
        boolean canBorrow() {
            return false;
        }
    };

    boolean canBorrow() { return false;}
    public static AssetState fromString(String value) {
        if (value != null) {
            for (AssetState condition : AssetState.values()) {
                if (value.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
