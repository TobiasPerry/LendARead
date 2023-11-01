package ar.edu.itba.paw.webapp.miscellaneous;

public class Vnd {
    public Vnd() {
        throw new AssertionError();
    }
    public static final String VND_PREFIX = "application/vnd.";
    public static final String VND_SUFFIX = "+json";
    public static final String VND_USER = VND_PREFIX + "user.v1" + VND_SUFFIX;

    public static final String VND_USER_CHANGE_PASSWORD = VND_PREFIX + "userChangePassword.v1" + VND_SUFFIX;

    public static final String VND_RESET_PASSWORD = VND_PREFIX + "resetPassword.v1" + VND_SUFFIX;

    public static final String VND_ASSET_INSTANCE_SEARCH = VND_PREFIX + "assetInstanceSearch.v1" + VND_SUFFIX;

    public static final String VND_ASSET_INSTANCE = VND_PREFIX + "assetInstance.v1" + VND_SUFFIX;

    public static final String VND_LOCATION = VND_PREFIX + "location.v1" + VND_SUFFIX;

    public static final String VND_USER_LENDER_REVIEW = VND_PREFIX + "userLenderReview.v1" + VND_SUFFIX;

    public static final String VND_USER_BORROWER_REVIEW = VND_PREFIX + "userBorrowerReview.v1" + VND_SUFFIX;

}
