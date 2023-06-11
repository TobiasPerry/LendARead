package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import java.util.List;

public interface LocationsService {
    void addLocation(final LocationImpl lc);

    void addLocation(final int id, final String name, final String locality, final String province, final String country, final String zipcode, final UserImpl user);
    LocationImpl getLocation(final int locationId);

    List<LocationImpl> getLocations(final UserImpl user);

    void editLocation(final LocationImpl lc);

    void deleteLocation(final LocationImpl lc);

    public List<LocationImpl> getLocationsById(final int userId) throws UserNotFoundException;

    LocationImpl editLocationById(final int locationId);
    void deleteLocationById(final int locationId) throws UserNotFoundException;
}
