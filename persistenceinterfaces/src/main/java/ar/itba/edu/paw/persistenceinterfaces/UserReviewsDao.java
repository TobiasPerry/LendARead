package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

public interface UserReviewsDao {

    void addReview(final UserReview newReview);

    double getRating(final UserImpl user);

    PagingImpl<UserReview> getUserReviewsAsBorrower(int pageNum, int itemsPerPage,final UserImpl recipient);
    PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage, final UserImpl reviewer);

}
