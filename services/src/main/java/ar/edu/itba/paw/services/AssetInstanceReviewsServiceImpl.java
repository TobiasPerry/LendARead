package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.AssetInstanceReviewsService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstanceReview;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.assetLendingContext.LendingState;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
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
    public AssetInstanceReview addReview(final int assetId,final int lendingId,final String review,final int rating) throws  UserNotFoundException, UnableToAddReviewException {
        Lending lending;
        lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId).orElseThrow(UnableToAddReviewException::new);
        if (lending.getAssetInstance().getId() != assetId) {
            throw new UnableToAddReviewException();
        }
        AssetInstanceReview assetInstanceReview = new AssetInstanceReview(lending, review, userService.getCurrentUser(),rating) ;
       assetInstanceReviewsDao.addReview(assetInstanceReview);
       LOGGER.info("Asset review added for lending {}", assetInstanceReview.getLending().getId());
       return assetInstanceReview;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<AssetInstanceReview> getReviewById(int reviewId)  {
        return assetInstanceReviewsDao.getReviewById(reviewId);
    }

    @Transactional
    @Override
    public void deleteReviewById(int reviewId) throws AssetInstanceReviewNotFoundException {
        AssetInstanceReview assetInstanceReview = assetInstanceReviewsDao.getReviewById(reviewId).orElseThrow(AssetInstanceReviewNotFoundException::new);
        assetInstanceReviewsDao.deleteReview(assetInstanceReview);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean canReview(final int assetInstanceId,final int lendingId) throws UserNotFoundException {
        Optional<AssetInstanceReview> assetInstanceReview = assetInstanceReviewsDao.getReviewByLendingId(lendingId);
        Optional<Lending> lendingOptional = userAssetInstanceService.getBorrowedAssetInstance(lendingId);
        if (!lendingOptional.isPresent())return false;
        Lending lending = lendingOptional.get();
        return !assetInstanceReview.isPresent() && lending.getActive().equals(LendingState.FINISHED) && lending.getUserReference().getEmail().equals(userService.getCurrentUser().getEmail()) && lending.getAssetInstance().getId() == assetInstanceId;

    }
    @Transactional(readOnly = true)
    @Override
    public PagingImpl<AssetInstanceReview> getAssetInstanceReviewsById(int pageNum, int itemsPerPage,int assetInstanceId) throws AssetInstanceNotFoundException {
        AssetInstance assetInstance = assetInstanceService.getAssetInstance(assetInstanceId).orElseThrow(AssetInstanceNotFoundException::new);
        return assetInstanceReviewsDao.getAssetInstanceReviews(pageNum,itemsPerPage,assetInstance);
    }
}
