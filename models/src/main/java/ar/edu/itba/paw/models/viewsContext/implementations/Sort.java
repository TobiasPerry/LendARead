package ar.edu.itba.paw.models.viewsContext.implementations;

public enum Sort {

    AUTHOR_NAME(),
    TITLE_NAME(),
    RECENT();

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
