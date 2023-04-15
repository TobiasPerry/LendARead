package ar.edu.itba.paw.models.assetExistanceContext.implementations;

public enum PhysicalCondition {
    ASNEW("Nuevo"),
    FINE("Casi Nuevo"),
    VERYGOOD("Muy Bueno"),
    GOOD("Bueno"),
    FAIR("Aceptable"),
    POOR("Pobre"),
    EXLIBRARY("Ex-Biblioteca"),
    BOOKCLUB("Club de Lectura"),
    BINDINGCOPY("Copia Encuadernada");

    PhysicalCondition(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    public static PhysicalCondition fromString(String value) {
        if (value != null) {
            for (PhysicalCondition condition : PhysicalCondition.values()) {
                if (value.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }

    private final String nameSpanish;

    public String nameSpanish() {
        return this.nameSpanish;
    }
}
