export const extractDate = (dateString) => {
    const [year, month, day] = dateString.split('-');
    return {day: day, month: month, year: year}
}

export class Vnd {
    private constructor() {}

    public static readonly VND_PREFIX = "application/vnd.";
    public static readonly VND_SUFFIX = "+json";

    public static readonly VND_USER = Vnd.VND_PREFIX + "user.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_RESET_PASSWORD = Vnd.VND_PREFIX + "resetPassword.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_ASSET_INSTANCE = Vnd.VND_PREFIX + "assetInstance.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_LOCATION = Vnd.VND_PREFIX + "location.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_USER_LENDER_REVIEW = Vnd.VND_PREFIX + "userLenderReview.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_USER_BORROWER_REVIEW = Vnd.VND_PREFIX + "userBorrowerReview.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_ASSET_INSTANCE_REVIEW = Vnd.VND_PREFIX + "assetInstanceReview.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_ASSET_INSTANCE_LENDING = Vnd.VND_PREFIX + "assetInstanceLending.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_ASSET_INSTANCE_LENDING_STATE = Vnd.VND_PREFIX + "assetInstanceLendingState.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_ASSET = Vnd.VND_PREFIX + "asset.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_LANGUAGE = Vnd.VND_PREFIX + "language.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_ROOT = Vnd.VND_PREFIX + "root.v1" + Vnd.VND_SUFFIX;
    public static readonly VND_VALIDATION_ERROR = Vnd.VND_PREFIX + "validationError.v1" + Vnd.VND_SUFFIX + ";charset=UTF-8";
}

export default Vnd;
