package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public PagingImpl<Location> getLocations(final Integer userId,final int page,final int itemsPerPage) {
        StringBuilder sb = new StringBuilder("SELECT id FROM location l ");
        if(userId != null){
            sb.append("WHERE l.owner= :userId ");
        }
        final int offset = (page - 1) * itemsPerPage;
        String pagination = " LIMIT :limit OFFSET :offset ";
        final Query queryCount = entityManager.createNativeQuery(sb.toString());
        sb.append(pagination);
        final Query queryNative  = entityManager.createNativeQuery(sb.toString());
        if (userId != null) {
            queryCount.setParameter("userId", userId);
            queryNative.setParameter("userId", userId);
        }
        queryNative.setParameter("limit", itemsPerPage);
        queryNative.setParameter("offset", offset);


        @SuppressWarnings("unchecked")
        final List<Integer> ids = (List<Integer>) queryCount.getResultList().stream().map(
                n -> (Integer) ((Number) n).intValue()).collect(Collectors.toList());
        final int totalPages = (int) Math.ceil((double) ((Number) ids.size()).longValue() / itemsPerPage);

        @SuppressWarnings("unchecked")
        List<Integer> list = (List<Integer>) queryNative.getResultList().stream().map(
                n -> (Integer) ((Number) n).intValue()).collect(Collectors.toList());

        // In case of empty result -> Return a Page with empty lists
        if (list.isEmpty())
            return new PagingImpl<>(new ArrayList<>(), 0, 0);

        // Get the AssetInstances that match those IDs for given page
        final TypedQuery<Location> query = entityManager.createQuery("FROM Location as l  WHERE l.id IN (:ids) ORDER BY l.id " , Location.class);
        query.setParameter("ids", list);
        List<Location> locations = query.getResultList();

        return new PagingImpl<>(locations, page, totalPages);

    }


    @Override
    public Location getLocation(int locationId) {
        return entityManager.find(Location.class, locationId);
    }

    @Override
    public Location editLocation(Location lc) {
        return entityManager.merge(lc);
    }



}
