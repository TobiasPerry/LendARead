package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.exceptions.UserReviewNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

public interface UserReviewsService {
     UserReview addReview(final int lendingId, final String reviewer,final String recipient, final String review, final int rating) throws AssetInstanceNotFoundException, UserNotFoundException, LendingNotFoundException;

    boolean lenderCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException, LendingNotFoundException;

    boolean borrowerCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException, LendingNotFoundException;


    boolean userHasReview(final int lendingId, final String user);

    UserReview getUserReviewAsLender(final String email,final int reviewId) throws UserReviewNotFoundException, UserNotFoundException;

    UserReview getUserReviewAsBorrower(final String email,final int reviewId) throws UserReviewNotFoundException, UserNotFoundException;


    PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage, UserImpl recipient);

    PagingImpl<UserReview> getUserReviewsAsLenderById(int pageNum, int itemsPerPage, final int borrowerId) throws UserNotFoundException;

    PagingImpl<UserReview> getUserReviewsAsBorrower(int pageNum, int itemsPerPage, UserImpl recipient);

    PagingImpl<UserReview> getUserReviewsAsReviewerById(final int pageNum, final int itemsPerPage, int reviewerId) throws UserNotFoundException;
}
