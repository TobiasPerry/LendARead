package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class LocationsDaoJpaImpl implements LocationDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Location addLocation(Location lc) {
        entityManager.persist(lc);
        return lc;
    }

    @Override
    public List<Location> getLocations(User user) {
        TypedQuery<Location> query = entityManager.createQuery("SELECT l FROM Location l WHERE l.userReference = :user AND l.active = TRUE", Location.class);
        query.setParameter("user", user);
        return query.getResultList();
    }


    @Override
    public Location getLocation(int locationId) {
        return entityManager.find(Location.class, locationId);
    }

    @Override
    public Location editLocation(Location lc) {
        return entityManager.merge(lc);
    }

    @Override
    public void deleteLocation(Location lc) {
        lc.setActive(false);
    }

}
