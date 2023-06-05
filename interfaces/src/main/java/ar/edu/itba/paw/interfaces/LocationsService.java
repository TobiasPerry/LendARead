package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import java.util.List;

public interface LocationsService {
    LocationImpl addLocation(LocationImpl lc);

    LocationImpl getLocation(int locationId);

    List<LocationImpl> getLocations(UserImpl user);

    LocationImpl editLocation(LocationImpl lc);

    public List<LocationImpl> getLocationsById(int userId) throws UserNotFoundException;

    LocationImpl editLocationById(int locationId);
}
