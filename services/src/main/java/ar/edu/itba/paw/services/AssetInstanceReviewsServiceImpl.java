package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.AssetInstanceReviewNotFoundException;
import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceReviewsService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.utils.HttpStatusCodes;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceReviewsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AssetInstanceReviewsServiceImpl implements AssetInstanceReviewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewsServiceImpl.class);

    private final AssetInstanceReviewsDao assetInstanceReviewsDao;

    private final AssetInstanceService assetInstanceService;

    private final UserAssetInstanceService userAssetInstanceService;

    private final UserService userService;

    @Autowired
    public AssetInstanceReviewsServiceImpl(final AssetInstanceReviewsDao assetInstanceReviewsDao,final AssetInstanceService assetInstanceService,final UserAssetInstanceService userAssetInstanceService,final UserService userService) {
        this.assetInstanceReviewsDao = assetInstanceReviewsDao;
        this.assetInstanceService = assetInstanceService;
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public AssetInstanceReview addReview(final int assetId,final int lendingId,final String review,final int rating) throws AssetInstanceNotFoundException, UserNotFoundException, LendingNotFoundException {
        LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);
        if(lending.getAssetInstance().getId() != assetId){
            throw new LendingNotFoundException(HttpStatusCodes.BAD_REQUEST);
        }
        AssetInstanceReview assetInstanceReview = new AssetInstanceReview(lending, review, userService.getUser(userService.getCurrentUser()),rating) ;
       assetInstanceReviewsDao.addReview(assetInstanceReview);
       LOGGER.info("Asset review added for lending {}", assetInstanceReview.getLending().getId());
       return assetInstanceReview;
    }

    @Transactional(readOnly = true)
    @Override
    public double getRating(final int assetInstanceId) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = assetInstanceService.getAssetInstance(assetInstanceId);
        return assetInstanceReviewsDao.getRating(assetInstance);
    }

    @Transactional(readOnly = true)
    @Override
    public double getRatingById(int assetInstanceId) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = assetInstanceService.getAssetInstance(assetInstanceId);
        return assetInstanceReviewsDao.getRating(assetInstance);
    }

    @Override
    public AssetInstanceReview getReviewById(int reviewId) throws AssetInstanceReviewNotFoundException {
        return assetInstanceReviewsDao.getReviewById(reviewId).orElseThrow(() -> new AssetInstanceReviewNotFoundException(HttpStatusCodes.NOT_FOUND));
    }

    @Override
    public void deleteReviewById(int reviewId) throws AssetInstanceReviewNotFoundException {
        AssetInstanceReview assetInstanceReview = assetInstanceReviewsDao.getReviewById(reviewId).orElseThrow(() -> new AssetInstanceReviewNotFoundException(HttpStatusCodes.BAD_REQUEST));
        assetInstanceReviewsDao.deleteReview(assetInstanceReview);
    }

    @Override
    public boolean canReview(final int lendingId) throws LendingNotFoundException {
        Optional<AssetInstanceReview> assetInstanceReview = assetInstanceReviewsDao.getReviewByLendingId(lendingId);
        LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);
        return !assetInstanceReview.isPresent() && lending.getActive().equals(LendingState.FINISHED) && lending.getUserReference().getEmail().equals(userService.getCurrentUser());

    }
    @Transactional(readOnly = true)
    @Override
    public PagingImpl<AssetInstanceReview> getAssetInstanceReviewsById(int pageNum, int itemsPerPage,int assetInstanceId) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = assetInstanceService.getAssetInstance(assetInstanceId);
        return assetInstanceReviewsDao.getAssetInstanceReviews(pageNum,itemsPerPage,assetInstance);
    }
}
