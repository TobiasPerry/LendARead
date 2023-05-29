package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;

import java.util.List;

public interface UserReviewsDao {

    void addReview(final UserReview newReview);

    double getRating(final UserImpl user);

    List<UserReview> getUserReviewsAsRecipient(final UserImpl recipient);

    List<UserReview> getUserReviewsAsReviewer(final UserImpl reviewer);

}
