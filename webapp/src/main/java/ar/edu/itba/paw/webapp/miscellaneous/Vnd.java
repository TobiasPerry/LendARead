package ar.edu.itba.paw.webapp.miscellaneous;

public class Vnd {
    public Vnd() {
        throw new AssertionError();
    }
    public static final String VND_PREFIX = "application/vnd.";
    public static final String VND_SUFFIX = "+json";
    public static final String VND_USER = VND_PREFIX + "user.v1" + VND_SUFFIX;

    public static final String VND_RESET_PASSWORD = VND_PREFIX + "resetPassword.v1" + VND_SUFFIX;

    public static final String VND_ASSET_INSTANCE = VND_PREFIX + "assetInstance.v1" + VND_SUFFIX;

    public static final String VND_LOCATION = VND_PREFIX + "location.v1" + VND_SUFFIX;

    public static final String VND_USER_LENDER_REVIEW = VND_PREFIX + "userLenderReview.v1" + VND_SUFFIX;

    public static final String VND_USER_BORROWER_REVIEW = VND_PREFIX + "userBorrowerReview.v1" + VND_SUFFIX;
    public static final String VND_ASSET_INSTANCE_REVIEW = VND_PREFIX + "assetInstanceReview.v1" + VND_SUFFIX;
    public static final String VND_ASSET_INSTANCE_LENDING = VND_PREFIX + "assetInstanceLending.v1" + VND_SUFFIX;
    public static final String VND_ASSET_INSTANCE_LENDING_STATE = VND_PREFIX + "assetInstanceLendingState.v1" + VND_SUFFIX;
    public static final String VND_ASSET = VND_PREFIX + "asset.v1" + VND_SUFFIX;
    public static final String VND_LANGUAGE = VND_PREFIX + "language.v1" + VND_SUFFIX;
    public static final String VND_ROOT = VND_PREFIX + "root.v1" + VND_SUFFIX;
    public static final String VND_VALIDATION_ERROR = VND_PREFIX + "validationError.v1" + VND_SUFFIX;

}
