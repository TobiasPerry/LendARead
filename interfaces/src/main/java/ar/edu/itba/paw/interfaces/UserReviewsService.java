package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.List;

public interface UserReviewsService {
    void addReview(final UserReview userReview);

    double getRating(final User user);

    List<UserReview> getUserReviewsAsRecipient(final User recipient);

    List<UserReview> getUserReviewsAsReviewer(final User reviewer);
}
