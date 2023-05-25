package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

import java.util.List;

public interface AssetInstanceReviewsDao {

    void addReview(final AssetInstanceReview assetInstanceReview);

    double getRating(final AssetInstance assetInstance);

    List<AssetInstanceReview> getAssetInstanceReviews(final AssetInstance assetInstance);
}
