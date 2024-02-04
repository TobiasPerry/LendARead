package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.userContext.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.util.Optional;

public interface UserReviewsDao {

    void addReview(final UserReview newReview);

    Optional<UserReview> getUserReviewsByLendingIdAndUser(final int lendingId, final String user);

    PagingImpl<UserReview> getUserReviewsAsBorrower(int pageNum, int itemsPerPage, final User recipient);

    PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage, final User reviewer);

    Optional<UserReview> getUserReviewAsLender(final int userId, final int reviewId) ;

    Optional<UserReview> getUserReviewAsBorrower(final int userId, final int reviewId) ;

}
