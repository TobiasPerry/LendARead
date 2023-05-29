package ar.edu.itba.paw.models.userContext.implementations;

public enum Behaviour {
    LENDER(),
    BORROWER();

    public static Behaviour fromString(String value) {
        if (value != null) {
            value = value.toUpperCase();
            for (Behaviour condition : Behaviour.values()) {
                String upperValue = value.toUpperCase();
                if (upperValue.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
