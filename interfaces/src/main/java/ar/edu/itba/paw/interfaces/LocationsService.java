package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.util.Optional;

public interface LocationsService {
    void addLocation(final Location lc);

    Location addLocation(final String name, final String locality, final String province, final String country, final String zipcode) throws UserNotFoundException;
    Location getLocation(final int locationId) throws LocationNotFoundException;
    void editLocation(final Location lc);
    void deleteLocation(final Location lc) throws LocationNotFoundException;
    Location editLocationById(final int locationId, final Optional<String> name,final Optional<String> locality,final Optional<String> province,final Optional<String> country,final Optional<String> zipcode) throws LocationNotFoundException;
    PagingImpl<Location> getLocations(final Integer userId, final int page, final int itemsPerPage) ;
    void deleteLocationById(final int locationId) throws  LocationNotFoundException;
}
