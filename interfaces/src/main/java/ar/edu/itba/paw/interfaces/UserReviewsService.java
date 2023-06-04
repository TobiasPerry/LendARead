package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;

import java.util.List;

public interface UserReviewsService {
    void addReview(final UserReview userReview);

    boolean lenderCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException;
    boolean borrowerCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException;

    double getRating(final UserImpl user);
    double getRatingById(final int userId)  throws UserNotFoundException;

    List<UserReview> getUserReviewsAsRecipient(final UserImpl recipient);
    List<UserReview> getUserReviewsAsRecipientById(final int recipientId)  throws UserNotFoundException;

    List<UserReview> getUserReviewsAsReviewer(final UserImpl reviewer);
    List<UserReview> getUserReviewsAsReviewerById(final int reviewerId)  throws UserNotFoundException;
}
