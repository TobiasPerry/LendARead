package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.userContext.interfaces.User;
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
    public void addReview(UserReview userReview) {
        userReviewsDao.addReview(userReview);
    }

    @Override
    public double getRating(User user) {
        return userReviewsDao.getRating(user);
    }

    @Override
    public List<UserReview> getUserReviewsAsRecipient(User recipient) {
        return userReviewsDao.getUserReviewsAsRecipient(recipient);
    }

    @Override
    public List<UserReview> getUserReviewsAsReviewer(User reviewer) {
        return userReviewsDao.getUserReviewsAsReviewer(reviewer);
    }
}
