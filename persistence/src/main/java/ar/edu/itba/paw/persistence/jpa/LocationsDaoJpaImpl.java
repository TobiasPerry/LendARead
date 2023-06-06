package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class LocationsDaoJpaImpl implements LocationDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LocationImpl addLocation(LocationImpl lc) {
        entityManager.persist(lc);
        return lc;
    }

    @Override
    public List<LocationImpl> getLocations(UserImpl user) {
        TypedQuery<LocationImpl> query = entityManager.createQuery("SELECT l FROM LocationImpl l WHERE l.userReference = :user", LocationImpl.class);
        query.setParameter("user", user);
        return query.getResultList();
    }


    @Override
    public LocationImpl getLocation(int locationId) {
        return entityManager.find(LocationImpl.class, locationId);
    }

    @Override
    public LocationImpl editLocation(LocationImpl lc) {
        return entityManager.merge(lc);
    }

}
