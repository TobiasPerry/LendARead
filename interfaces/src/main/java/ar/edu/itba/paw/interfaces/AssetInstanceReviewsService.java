package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.AssetInstanceReviewNotFoundException;
import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

public interface AssetInstanceReviewsService {

    AssetInstanceReview addReview(final int assetId,final int lendingId,final String review,final int rating) throws AssetInstanceNotFoundException, UserNotFoundException, LendingNotFoundException;
    double getRating(final int assetInstanceId) throws AssetInstanceNotFoundException;

    double getRatingById(final int assetInstanceId) throws AssetInstanceNotFoundException;

    AssetInstanceReview getReviewById(final int reviewId) throws AssetInstanceReviewNotFoundException;

    void deleteReviewById(final int reviewId) throws AssetInstanceReviewNotFoundException;
    PagingImpl<AssetInstanceReview> getAssetInstanceReviewsById(int pageNum, int itemsPerPage, int assetInstanceId) throws AssetInstanceNotFoundException;
    boolean canReview(final int assetInstanceId,final int lendingId) throws LendingNotFoundException;
}
