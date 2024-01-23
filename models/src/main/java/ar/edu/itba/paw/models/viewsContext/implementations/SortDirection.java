package ar.edu.itba.paw.models.viewsContext.implementations;

public enum SortDirection {
    ASCENDING("ASC"),
    DESCENDING("DESC");

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
    private String value = "ASC";
     SortDirection(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
