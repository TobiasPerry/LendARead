package ar.edu.itba.paw.models.assetLendingContext.implementations;

public enum AssetState {
    PUBLIC(){
        @Override
        public boolean isPublic() {
            return true;
        }
    },
    PRIVATE(){
        @Override
        public boolean isPrivate() {
            return true;
        }

    },
    BORROWED(){
        @Override
        public boolean isBorrowed() {
            return false;
        }
    };

    public boolean isPublic() { return false;}
    public boolean isBorrowed() { return false;}
    public boolean isPrivate() { return true; }

    public static AssetState fromString(String value) {
        if (value != null) {
            for (AssetState condition : AssetState.values()) {
                if (value.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
