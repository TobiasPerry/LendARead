package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserReviewsServiceImpl implements UserReviewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewsServiceImpl.class);

    private final UserReviewsDao userReviewsDao;

    private final UserAssetInstanceService userAssetInstanceService;
    private final UserDao userDao;

    private final UserService userService;
    @Autowired
    public UserReviewsServiceImpl(final UserReviewsDao userReviewsDao,final UserDao userDao, final UserAssetInstanceService userAssetInstanceService, final UserService userService){
        this.userReviewsDao = userReviewsDao;
        this.userDao = userDao;
        this.userService = userService;
        this.userAssetInstanceService = userAssetInstanceService;
    }


    @Transactional
    @Override
    public void addReview(UserReview userReview) {
        userReviewsDao.addReview(userReview);
    }

    @Override
    public boolean lenderCanReview(final int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException {
        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);
        return lending.getAssetInstance().getOwner().equals(userService.getUser(userService.getCurrentUser()));
    }

    @Override
    public boolean borrowerCanReview(int lendingId) throws AssetInstanceNotFoundException, UserNotFoundException {
        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);
        return lending.getUserReference().equals(userService.getUser(userService.getCurrentUser()));
    }

    @Transactional
    @Override
    public double getRating(UserImpl user) {
        return userReviewsDao.getRating(user);
    }

    private UserImpl getUser(int userId) throws UserNotFoundException {
        Optional<UserImpl> user = userDao.getUser(userId);
        if(!user.isPresent())
            throw new UserNotFoundException("not found user to get the rating");
        return user.get();
    }

    @Transactional
    @Override
    public double getRatingById(int userId) throws UserNotFoundException {
        return getRating(getUser(userId));
    }

    @Transactional
    @Override
    public List<UserReview> getUserReviewsAsRecipient(UserImpl recipient) {
        return userReviewsDao.getUserReviewsAsRecipient(recipient);
    }

    @Transactional
    @Override
    public List<UserReview> getUserReviewsAsRecipientById(int recipientId) throws UserNotFoundException{
        return getUserReviewsAsRecipient(getUser(recipientId));
    }

    @Transactional
    @Override
    public List<UserReview> getUserReviewsAsReviewer(UserImpl reviewer) {
        return userReviewsDao.getUserReviewsAsReviewer(reviewer);
    }

    @Transactional
    @Override
    public List<UserReview> getUserReviewsAsReviewerById(int reviewerId) throws UserNotFoundException {
        return getUserReviewsAsReviewer(getUser(reviewerId));
    }
}
