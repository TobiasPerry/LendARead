package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.userContext.implementations.UserReview;

import java.util.List;

public interface UserReviewsService {
    void addReview(final String review, final int rating, final int lendingId, final int reviewerId, final int recipientId);

    double getRating(final int userId);

    List<UserReview> getUserReviewsAsRecipient(final int recipientId);

    List<UserReview> getUserReviewsAsReviewer(final int reviewerId);
}
