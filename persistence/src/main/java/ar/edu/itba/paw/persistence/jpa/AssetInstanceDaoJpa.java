package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class AssetInstanceDaoJpa implements AssetInstanceDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public AssetInstance addAssetInstance(BookImpl book, UserImpl owner, LocationImpl location, int photoId, AssetInstance ai) {
        return null;
    }

    @Override
    public Optional<AssetInstance> getAssetInstance(int assetId) {
        return Optional.empty();
    }

    @Override
    public Boolean changeStatus(int lendingId, AssetState as) {
        return null;
    }

    @Override
    public Boolean changeStatusByLendingId(int lendingId, AssetState as) {
        return null;
    }

    @Override
    public Optional<Page> getAllAssetInstances(int pageNum, int itemsPerPage, SearchQuery searchQuery) {
        return Optional.empty();
    }
}
