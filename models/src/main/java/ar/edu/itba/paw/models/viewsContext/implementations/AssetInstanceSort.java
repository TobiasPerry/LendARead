package ar.edu.itba.paw.models.viewsContext.implementations;

public enum AssetInstanceSort {

    AUTHOR_NAME(),
    TITLE(),
    RECENT(),

    STATE(),

    LANGUAGE(),

    PHYSICAL_CONDITION();

    public static AssetInstanceSort fromString(String value) {
        if (value != null) {
            for (AssetInstanceSort assetInstanceSort : AssetInstanceSort.values()) {
                if (value.equalsIgnoreCase(assetInstanceSort.toString())) {
                    return assetInstanceSort;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }

}
