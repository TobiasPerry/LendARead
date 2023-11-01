package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.exceptions.UserReviewNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

public interface UserReviewsService {
     UserReview addReview(final int lendingId, final String reviewer,final String recipient, final String review, final int rating) throws AssetInstanceNotFoundException, UserNotFoundException ;

    boolean lenderCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException;

    boolean borrowerCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException;

    double getRating(final UserImpl user);

    double getRatingAsLender(UserImpl user);

    double getRatingAsBorrower(UserImpl user);

    boolean userHasReview(final int lendingId, final String user);

    UserReview getUserReviewAsLender(final int userId,final int reviewId) throws UserReviewNotFoundException;

    UserReview getUserReviewAsBorrower(final int userId,final int reviewId) throws UserReviewNotFoundException;

    double getRatingById(final int userId) throws UserNotFoundException;

    PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage, UserImpl recipient);

    PagingImpl<UserReview> getUserReviewsAsLenderById(int pageNum, int itemsPerPage, final int borrowerId) throws UserNotFoundException;

    PagingImpl<UserReview> getUserReviewsAsBorrower(int pageNum, int itemsPerPage, UserImpl recipient);

    PagingImpl<UserReview> getUserReviewsAsReviewerById(final int pageNum, final int itemsPerPage, int reviewerId) throws UserNotFoundException;
}
