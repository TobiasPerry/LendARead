package ar.edu.itba.paw.models.viewsContext.implementations;

public enum Sort {

    AUTHOR_NAME("b.author"),
    TITLE_NAME("b.title"),
    RECENT("ai.id");

    private final String postgresString;

    Sort(String postgresString){
        this.postgresString = postgresString;
    }

    public String getPostgresString() {
        return postgresString;
    }
}
