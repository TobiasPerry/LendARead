package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToAddReviewException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.exceptions.UserReviewNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.assetLendingContext.LendingState;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.userContext.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.UserReviewsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserReviewsServiceImpl implements UserReviewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewsServiceImpl.class);

    private final UserReviewsDao userReviewsDao;

    private final UserAssetInstanceService userAssetInstanceService;

    private final UserService userService;

    @Autowired
    public UserReviewsServiceImpl(final UserReviewsDao userReviewsDao, final UserAssetInstanceService userAssetInstanceService, final UserService userService) {
        this.userReviewsDao = userReviewsDao;
        this.userService = userService;
        this.userAssetInstanceService = userAssetInstanceService;
    }


    @Transactional
    @Override
    public UserReview addReview(final int lendingId, final int recipient, final String review, final int rating) throws UserNotFoundException, UnableToAddReviewException {
        Lending lending ;
        User recipientUser ;
        try{
            lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId).orElseThrow(UnableToAddReviewException::new);
            recipientUser = userService.getUserById(recipient);
        }catch ( UserNotFoundException  e) {
            throw new UnableToAddReviewException();
        }
        User reviewerUser = userService.getCurrentUser();
        UserReview userReview = new UserReview( review, rating,  reviewerUser,recipientUser, lending);
        userReviewsDao.addReview(userReview);
        LOGGER.info("Review added");
        return userReview;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean lenderCanReview(final int recipientId,final int lendingId) throws UserNotFoundException, LendingNotFoundException {
        final Lending lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId).orElseThrow(LendingNotFoundException::new);
        boolean hasReview = userHasReview(lendingId, userService.getCurrentUser().getEmail());

        return !hasReview && lending.getAssetInstance().getOwner().equals(userService.getCurrentUser()) && lending.getUserReference().getId() == recipientId && lending.getActive().equals(LendingState.FINISHED);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean borrowerCanReview(final int recipientId,int lendingId) throws UserNotFoundException, LendingNotFoundException {
        final Lending lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId).orElseThrow(LendingNotFoundException::new);
        boolean hasReview = userHasReview(lendingId, userService.getCurrentUser().getEmail());
        return !hasReview && lending.getUserReference().equals(userService.getCurrentUser()) && lending.getAssetInstance().getOwner().getId() == recipientId && lending.getActive().equals(LendingState.FINISHED);
    }


    @Transactional(readOnly = true)
    @Override
    public boolean userHasReview(int lendingId, String user) {
        Optional<UserReview> review = userReviewsDao.getUserReviewsByLendingIdAndUser(lendingId, user);
        return review.isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public UserReview getUserReviewAsLender(final int id, int reviewId) throws UserReviewNotFoundException, UserNotFoundException {
        return userReviewsDao.getUserReviewAsLender(userService.getUserById(id).getId(),reviewId).orElseThrow(UserReviewNotFoundException::new);

    }

    @Transactional(readOnly = true)
    @Override
    public UserReview getUserReviewAsBorrower(final int id, int reviewId) throws UserReviewNotFoundException, UserNotFoundException {
        return userReviewsDao.getUserReviewAsBorrower(userService.getUserById(id).getId(),reviewId).orElseThrow(UserReviewNotFoundException::new);
    }


    @Transactional(readOnly = true)
    @Override
    public PagingImpl<UserReview> getUserReviewsAsLender(int pageNum, int itemsPerPage, User recipient) {
        return userReviewsDao.getUserReviewsAsLender(pageNum, itemsPerPage, recipient);
    }

    @Transactional(readOnly = true)
    @Override
    public PagingImpl<UserReview> getUserReviewsAsLenderById(int pageNum, int itemsPerPage, int recipientId) throws UserNotFoundException {
        return getUserReviewsAsLender(pageNum, itemsPerPage, userService.getUserById(recipientId));
    }

    @Transactional(readOnly = true)
    @Override
    public PagingImpl<UserReview> getUserReviewsAsBorrower(int pageNum, int itemsPerPage, User recipient) {
        return userReviewsDao.getUserReviewsAsBorrower(pageNum, itemsPerPage, recipient);
    }

    @Transactional(readOnly = true)
    @Override
    public PagingImpl<UserReview> getUserReviewsAsReviewerById(final int pageNum, final int itemsPerPage, int reviewerId) throws UserNotFoundException {
        return getUserReviewsAsBorrower(pageNum, itemsPerPage, userService.getUserById(reviewerId));
    }
}
