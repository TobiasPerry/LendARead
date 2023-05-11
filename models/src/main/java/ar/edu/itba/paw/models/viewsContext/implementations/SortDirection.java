package ar.edu.itba.paw.models.viewsContext.implementations;

public enum SortDirection {
    ASCENDING(),
    DESCENDING(),
    DEFAULT();

    public static SortDirection fromString(String value) {
        if (value != null) {
            for (SortDirection sortDirection : SortDirection.values()) {
                if (value.equalsIgnoreCase(sortDirection.toString())) {
                    return sortDirection;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
