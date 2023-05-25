package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.userContext.interfaces.User;

import java.util.List;

public interface UserReviewsDao {

    void addReview(final UserReview newReview);

    double getRating(final User user);

    List<UserReview> getUserReviewsAsRecipient(final User recipient);

    List<UserReview> getUserReviewsAsReviewer(final User reviewer);

}
