package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;

import java.util.List;

public interface UserReviewsService {
    void addReview(final UserReview userReview);

    double getRating(final UserImpl user);
    double getRatingById(final int userId)  throws UserNotFoundException;

    List<UserReview> getUserReviewsAsRecipient(final UserImpl recipient);
    List<UserReview> getUserReviewsAsRecipientById(final int recipientId)  throws UserNotFoundException;

    List<UserReview> getUserReviewsAsReviewer(final UserImpl reviewer);
    List<UserReview> getUserReviewsAsReviewerById(final int reviewerId)  throws UserNotFoundException;
}
