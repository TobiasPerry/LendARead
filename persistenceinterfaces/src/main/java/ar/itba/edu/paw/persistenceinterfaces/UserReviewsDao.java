package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.UserReview;

import java.util.List;

public interface UserReviewsDao {

    void addReview(final String review, final int rating, final int lendingId, final int reviewerId, final int recipientId);

    boolean getRating(final int userId);

    List<UserReview> getUserReviews(final int userId);

}
