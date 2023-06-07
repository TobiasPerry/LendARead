package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

public interface AssetInstanceReviewsService {

    void addReview(final AssetInstanceReview assetInstanceReview);

    double getRating(final AssetInstanceImpl assetInstance);

    double getRatingById(final int assetInstanceId) throws AssetInstanceNotFoundException;

    PagingImpl<AssetInstanceReview> getAssetInstanceReviews(int pageNum, int itemsPerPage,AssetInstanceImpl assetInstance);

    PagingImpl<AssetInstanceReview> getAssetInstanceReviewsById(int pageNum, int itemsPerPage, int assetInstanceId) throws AssetInstanceNotFoundException;
}
