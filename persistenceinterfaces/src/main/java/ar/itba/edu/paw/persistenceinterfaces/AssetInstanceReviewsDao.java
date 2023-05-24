package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;

import java.util.List;

public interface AssetInstanceReviewsDao {

    void addReview(final int lendingId, final String message, final int reviewerId, final int rating);

    double getRating(final int assetInstanceId);

    List<AssetInstanceReview> getAssetInstanceReviews(final int assetInstanceId);
}
