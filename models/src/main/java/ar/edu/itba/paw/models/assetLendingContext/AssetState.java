package ar.edu.itba.paw.models.assetLendingContext;

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


    DELETED() {
        @Override
        public boolean isDeleted() {
            return true;
        }
    },
    ALL();


    public boolean isPublic() { return false;}
    public boolean isPrivate() { return false; }
    public boolean isDeleted() { return false;}

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
