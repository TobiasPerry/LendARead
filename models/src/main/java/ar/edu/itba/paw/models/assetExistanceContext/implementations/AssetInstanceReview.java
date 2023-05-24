package ar.edu.itba.paw.models.assetExistanceContext.implementations;

public class AssetInstanceReview {
    private int lendingId;
    private String message;
    private int reviewerId;

    private int rating;

    public AssetInstanceReview(int lendingId, String message, int reviewerId, int rating) {
        this.lendingId = lendingId;
        this.message = message;
        this.reviewerId = reviewerId;
        this.rating = rating;
    }
}
