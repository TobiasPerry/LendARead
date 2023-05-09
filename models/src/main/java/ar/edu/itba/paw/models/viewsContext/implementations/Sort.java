package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;

public enum Sort {

    AUTHOR_NAME("b.author"),
    TITLE_NAME("b.title"),
    RECENT("ai.id"),
    DEFAULT("ai.id");

    private final String postgresString;

    Sort(String postgresString){
        this.postgresString = postgresString;
    }

    public String getPostgresString() {
        return postgresString;
    }

    public static Sort fromString(String value) {
        if (value != null) {
            for (Sort sort : Sort.values()) {
                if (value.equalsIgnoreCase(sort.toString())) {
                    return sort;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
