package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.util.Optional;

public interface LocationDao {

    Location addLocation(Location lc);

    PagingImpl<Location> getLocations(final Integer userId, final int page, final int itemsPerPage);

    Optional<Location> getLocation(int location);

    Location editLocation(Location lc);

}
