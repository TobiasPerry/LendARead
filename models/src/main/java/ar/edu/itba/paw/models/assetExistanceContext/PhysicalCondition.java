package ar.edu.itba.paw.models.assetExistanceContext;

public enum PhysicalCondition {
    ASNEW(),
    FINE(),
    VERYGOOD(),
    GOOD(),
    FAIR(),
    POOR(),
    EXLIBRARY(),
    BOOKCLUB(),
    BINDINGCOPY();

    public static PhysicalCondition fromString(String value) {
        if (value != null) {
            value = value.toUpperCase();
            for (PhysicalCondition condition : PhysicalCondition.values()) {
                if (value.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }

}
