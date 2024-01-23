package ar.edu.itba.paw.models.viewsContext.implementations;

public enum LendingSort {

    TITLE(),
    LENDDATE(),
    DEVOLUTIONDATE(),

    BORROWER_USER(),
    LENDER_USER(),
    LENDING_STATUS();

    public static LendingSort fromString(String value) {
        if (value != null) {
            for (LendingSort lendingSort : LendingSort.values()) {
                if (value.equalsIgnoreCase(lendingSort.toString())) {
                    return lendingSort;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
