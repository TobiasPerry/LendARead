package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.util.Optional;

public interface AssetInstanceReviewsDao {

    void addReview(final AssetInstanceReview assetInstanceReview);

    double getRating(final AssetInstance assetInstance);

    PagingImpl<AssetInstanceReview> getAssetInstanceReviews(int pageNum, int itemsPerPage, AssetInstance assetInstance);

    Optional<AssetInstanceReview> getReviewById(final int reviewId);

    void deleteReview(final AssetInstanceReview reviewId);

    Optional<AssetInstanceReview> getReviewByLendingId(final int lendingId);
}
