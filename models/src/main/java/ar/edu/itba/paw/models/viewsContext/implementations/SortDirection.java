package ar.edu.itba.paw.models.viewsContext.implementations;

public enum SortDirection {
    ASCENDING("ASC"),
    DESCENDING("DESC");

    private final String postgresString;

    SortDirection(final String postgresString){
        this.postgresString = postgresString;
    }

    public String getPostgresString() {
        return postgresString;
    }
}
