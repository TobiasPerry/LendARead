package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.PageUserAssetsImpl;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;
import ar.itba.edu.paw.persistenceinterfaces.UserAssetsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAssetsDaoJpa implements UserAssetsDao {

    @PersistenceContext
    private EntityManager em;





    @Override
    public PageUserAssets<LendingImpl> getLendedAssets(int pageNumber, int itemsPerPage, String email, String filterAtribuite, String filterValue, String sortAtribuite, String direction) {
        //TODO DEBEMOS HACER LO DE LAS PAGINAS PERO NO LLEGUE
        String query = "SELECT l FROM LendingImpl l WHERE l.assetInstance.userReference.email = :email";
        List<LendingImpl> list =  em.createQuery(query, LendingImpl.class).setParameter("email", email).getResultList();

        return new PageUserAssetsImpl<>(list, 1, 1);
    }

    @Override
    public PageUserAssets<LendingImpl> getBorrowedAssets(int pageNumber, int itemsPerPage, String email, String filterAtribuite, String filterValue, String sortAtribuite, String direction) {
        //TODO DEBEMOS HACER LO DE LAS PAGINAS PERO NO LLEGUE

        String query = "SELECT l FROM LendingImpl l WHERE l.userReference.email = :email";
        List<LendingImpl> list =  em.createQuery(query, LendingImpl.class).setParameter("email", email).getResultList();

        return new PageUserAssetsImpl<>(list, 1, 1);
    }

    @Override
    public PageUserAssets<AssetInstanceImpl> getUsersAssets(int pageNumber, int itemsPerPage, String email, String filterAtribuite, String filterValue, String sortAtribuite, String direction) {
        //TODO DEBEMOS HACER LO DE LAS PAGINAS PERO NO LLEGUE

        String query = "SELECT a FROM AssetInstanceImpl a WHERE a.userReference.email = :email";
        List<AssetInstanceImpl> list =  em.createQuery(query, AssetInstanceImpl.class).setParameter("email", email).getResultList();
        return new PageUserAssetsImpl<>(list, 1, 1);
    }

    @Override
    public Optional<LendingImpl> getBorrowedAsset(int lendingId) {
        String query = "SELECT l FROM LendingImpl l WHERE l.id = :lendingId";
        List<LendingImpl> list =  em.createQuery(query, LendingImpl.class).setParameter("lendingId", new Long(lendingId)).getResultList();
        return list.stream().findFirst();
    }
}
