package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

public interface UserReviewsService {
    void addReview(final UserReview userReview);

    boolean lenderCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException;
    boolean borrowerCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException;

    double getRating(final UserImpl user);
    double getRatingById(final int userId)  throws UserNotFoundException;

    PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage,UserImpl recipient);
    PagingImpl<UserReview> getUserReviewsAsLenderById(int pageNum, int itemsPerPage,final int borrowerId)throws UserNotFoundException;

    PagingImpl<UserReview> getUserReviewsBorrower(int pageNum, int itemsPerPage, UserImpl reviewer);
    PagingImpl<UserReview> getUserReviewsAsReviewerById(final int pageNum,final int itemsPerPage,int reviewerId) throws UserNotFoundException ;
}
