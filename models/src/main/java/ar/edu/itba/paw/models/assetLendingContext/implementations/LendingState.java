package ar.edu.itba.paw.models.assetLendingContext.implementations;

public enum LendingState {
    ACTIVE, FINISHED, REJECTED;
    public static LendingState fromString(String value) {
        if (value != null) {
            for (LendingState condition : LendingState.values()) {
                if (value.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
