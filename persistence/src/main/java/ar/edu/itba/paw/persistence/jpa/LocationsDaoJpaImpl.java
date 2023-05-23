package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LocationsDaoJpaImpl implements LocationDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LocationImpl addLocation(LocationImpl lc) {
        entityManager.persist(lc);
        return lc;
    }
}
