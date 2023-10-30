package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import java.util.List;
import java.util.Optional;

public interface LocationsService {
    void addLocation(final LocationImpl lc);

    LocationImpl addLocation(final String name, final String locality, final String province, final String country, final String zipcode) throws UserNotFoundException;
    LocationImpl getLocation(final int locationId) throws LocationNotFoundException;

    List<LocationImpl> getLocations(final UserImpl user);

    void editLocation(final LocationImpl lc);

    void deleteLocation(final LocationImpl lc);

     List<LocationImpl> getLocationsById(final int userId) throws UserNotFoundException;

    LocationImpl editLocationById(int locationId, Optional<String> name, Optional<String> locality, Optional<String> province, Optional<String> country, Optional<String> zipcode) throws LocationNotFoundException;
    List<LocationImpl> getLocations();
    void deleteLocationById(final int locationId) throws  LocationNotFoundException;
}
