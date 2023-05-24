package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReviewsServiceImpl implements UserReviewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewsServiceImpl.class);

    private final UserReviewsDao userReviewsDao;

    @Autowired
    public UserReviewsServiceImpl(UserReviewsDao userReviewsDao) {
        this.userReviewsDao = userReviewsDao;
    }

    @Override
    public void addReview(final String review, final int rating, final int lendingId, final int reviewerId, final int recipientId) {
        userReviewsDao.addReview(review, rating, lendingId, reviewerId, recipientId);
    }

    @Override
    public double getRating(int userId) {
        return userReviewsDao.getRating(userId);
    }

    @Override
    public List<UserReview> getUserReviewsAsRecipient(final int recipientId) {
        return userReviewsDao.getUserReviewsAsRecipient(recipientId);
    }

    @Override
    public List<UserReview> getUserReviewsAsReviewer(final int reviewerId) {
        return userReviewsDao.getUserReviewsAsReviewer(reviewerId);
    }
}
