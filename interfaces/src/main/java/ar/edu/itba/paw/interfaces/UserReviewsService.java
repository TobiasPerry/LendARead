package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToAddReviewException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.exceptions.UserReviewNotFoundException;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.userContext.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

public interface UserReviewsService {
     UserReview addReview(final int lendingId, final int recipient, final String review, final int rating) throws UserNotFoundException, LendingNotFoundException, UnableToAddReviewException;

    boolean lenderCanReview(final int recipientId,final int lendingId) throws  UserNotFoundException, LendingNotFoundException;

    boolean borrowerCanReview(final int recipientId,final int lendingId) throws  UserNotFoundException, LendingNotFoundException;


    boolean userHasReview(final int lendingId, final String user);

    UserReview getUserReviewAsLender(final int id,final int reviewId) throws UserReviewNotFoundException, UserNotFoundException;

    UserReview getUserReviewAsBorrower(final int id,final int reviewId) throws UserReviewNotFoundException, UserNotFoundException;


    PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage, User recipient);

    PagingImpl<UserReview> getUserReviewsAsLenderById(int pageNum, int itemsPerPage, final int borrowerId) throws UserNotFoundException;

    PagingImpl<UserReview> getUserReviewsAsBorrower(int pageNum, int itemsPerPage, User recipient);

    PagingImpl<UserReview> getUserReviewsAsReviewerById(final int pageNum, final int itemsPerPage, int reviewerId) throws UserNotFoundException;
}
