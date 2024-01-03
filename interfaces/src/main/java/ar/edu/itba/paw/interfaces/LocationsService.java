package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.userContext.implementations.User;

import java.util.List;
import java.util.Optional;

public interface LocationsService {
    void addLocation(final Location lc);

    Location addLocation(final String name, final String locality, final String province, final String country, final String zipcode) throws UserNotFoundException;
    Location getLocation(final int locationId) throws LocationNotFoundException;

    List<Location> getLocations(final User user);

    void editLocation(final Location lc);

    void deleteLocation(final Location lc) throws LocationNotFoundException;

     List<Location> getLocationsById(final int userId) throws UserNotFoundException;

    Location editLocationById(int locationId, Optional<String> name, Optional<String> locality, Optional<String> province, Optional<String> country, Optional<String> zipcode) throws LocationNotFoundException;
    List<Location> getLocations();
    void deleteLocationById(final int locationId) throws  LocationNotFoundException;
}
