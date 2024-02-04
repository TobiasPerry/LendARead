package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstanceReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.util.Optional;

public interface AssetInstanceReviewsService {

    AssetInstanceReview addReview(final int assetId,final int lendingId,final String review,final int rating) throws  UserNotFoundException,  UnableToAddReviewException;
    Optional<AssetInstanceReview> getReviewById(final int reviewId) ;

    void deleteReviewById(final int reviewId) throws AssetInstanceReviewNotFoundException;
    PagingImpl<AssetInstanceReview> getAssetInstanceReviewsById(int pageNum, int itemsPerPage, int assetInstanceId) throws AssetInstanceNotFoundException;
    boolean canReview(final int assetInstanceId,final int lendingId) throws LendingNotFoundException, UserNotFoundException;
}
