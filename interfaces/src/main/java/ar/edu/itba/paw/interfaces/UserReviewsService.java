package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.List;

public interface UserReviewsService {
    void addReview(final UserReview userReview);

    double getRating(final User user);
    double getRatingById(final int userId)  throws UserNotFoundException;

    List<UserReview> getUserReviewsAsRecipient(final User recipient);
    List<UserReview> getUserReviewsAsRecipientById(final int recipientId)  throws UserNotFoundException;

    List<UserReview> getUserReviewsAsReviewer(final User reviewer);
    List<UserReview> getUserReviewsAsReviewerById(final int reviewerId)  throws UserNotFoundException;
}
