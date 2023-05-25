package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetInstanceReviewsService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceReviewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetInstanceReviewsServiceImpl implements AssetInstanceReviewsService {

    private final AssetInstanceReviewsDao assetInstanceReviewsDao;

    @Autowired
    public AssetInstanceReviewsServiceImpl(AssetInstanceReviewsDao assetInstanceReviewsDao) {
        this.assetInstanceReviewsDao = assetInstanceReviewsDao;
    }

    @Override
    public void addReview(AssetInstanceReview assetInstanceReview) {
       assetInstanceReviewsDao.addReview(assetInstanceReview);
    }

    @Override
    public double getRating(AssetInstance assetInstance) {
        return assetInstanceReviewsDao.getRating(assetInstance);
    }

    @Override
    public double getRatingById(int assetInstanceId) {
        return 0;
    }

    @Override
    public List<AssetInstanceReview> getAssetInstanceReviews(AssetInstance assetInstance) {
        return assetInstanceReviewsDao.getAssetInstanceReviews(assetInstance);
    }

    @Override
    public List<AssetInstanceReview> getAssetInstanceReviewsById(int assetInstanceId) {
        return null;
    }
}
