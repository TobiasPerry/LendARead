package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceReviewsDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssetInstanceReviewsDaoJpa implements AssetInstanceReviewsDao {

    @Override
    public void addReview(AssetInstanceReview assetInstanceReview) {

    }

    @Override
    public double getRating(AssetInstance assetInstance) {
        return 0;
    }

    @Override
    public List<AssetInstanceReview> getAssetInstanceReviews(AssetInstance assetInstance) {
        return null;
    }
}
