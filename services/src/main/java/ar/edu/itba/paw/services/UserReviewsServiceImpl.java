package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserReviewsServiceImpl implements UserReviewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewsServiceImpl.class);

    private final UserReviewsDao userReviewsDao;

    private final UserDao userDao;

    @Autowired
    public UserReviewsServiceImpl(UserReviewsDao userReviewsDao, UserDao userDao) {
        this.userReviewsDao = userReviewsDao;
        this.userDao = userDao;
    }


    @Override
    public void addReview(UserReview userReview) {
        userReviewsDao.addReview(userReview);
    }

    @Override
    public double getRating(User user) {
        return userReviewsDao.getRating(user);
    }

    private User getUser(int userId) throws UserNotFoundException {
        Optional<User> user = userDao.getUser(userId);
        if(!user.isPresent())
            throw new UserNotFoundException("not found user to get the rating");
        return user.get();
    }

    @Override
    public double getRatingById(int userId) throws UserNotFoundException {
        return getRating(getUser(userId));
    }

    @Override
    public List<UserReview> getUserReviewsAsRecipient(User recipient) {
        return userReviewsDao.getUserReviewsAsRecipient(recipient);
    }

    @Override
    public List<UserReview> getUserReviewsAsRecipientById(int recipientId) throws UserNotFoundException{
        return getUserReviewsAsRecipient(getUser(recipientId));
    }

    @Override
    public List<UserReview> getUserReviewsAsReviewer(User reviewer) {
        return userReviewsDao.getUserReviewsAsReviewer(reviewer);
    }

    @Override
    public List<UserReview> getUserReviewsAsReviewerById(int reviewerId) throws UserNotFoundException {
        return getUserReviewsAsReviewer(getUser(reviewerId));
    }
}
