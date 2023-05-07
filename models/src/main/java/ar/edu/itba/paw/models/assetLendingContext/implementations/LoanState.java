package ar.edu.itba.paw.models.assetLendingContext.implementations;

public enum LoanState {
    PENDING() {

    },
    CONFIRMED() {

    },
    DELAYED() {

    };

    public static LoanState fromString(String value) {
        if (value != null) {
            for (LoanState condition : LoanState.values()) {
                if (value.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
