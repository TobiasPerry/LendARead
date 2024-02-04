package ar.edu.itba.paw.models.assetLendingContext;

public enum LendingState {
    DELIVERED(){
        @Override
        public boolean getIsDelivered() {
            return true;
        }
    }, ACTIVE() {
        @Override
        public boolean getIsActive() {
            return true;
        }
    }, FINISHED() {
        @Override
        public boolean getIsFinished() {
            return true;
        }
    }, REJECTED() {
        @Override
        public boolean getIsRejected() {
            return true;
        };


    }, CANCELED() {
        @Override
        public boolean getIsCancel() {
            return true;
        }
        @Override
        public boolean lenderStates() {
            return false;
        }
    };

    public static void main(String[] args) {

    }

    public boolean getIsDelivered() { return false; }
    public boolean getIsActive() { return false; }

    public boolean getIsFinished() { return false; }

    public boolean getIsRejected() { return false; }
    public boolean getIsCancel() { return false; }

    public boolean lenderStates() { return true; }
    public static LendingState fromString(String value) {
        if (value != null) {
            for (LendingState condition : LendingState.values()) {
                if (value.equalsIgnoreCase(condition.toString())) {
                    return condition;
                }
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}
