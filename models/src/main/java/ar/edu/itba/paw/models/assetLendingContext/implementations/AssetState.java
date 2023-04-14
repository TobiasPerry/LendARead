package ar.edu.itba.paw.models.assetLendingContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;

public enum AssetState {
    PUBLIC(true),
    PRIVATE(false),
    BORROWED(false),

    FROZEN(false);

    private final boolean canBorrow;

    AssetState(boolean canBorrow){
        this.canBorrow = canBorrow;
    }
    public boolean canBorrow() { return canBorrow;}
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
