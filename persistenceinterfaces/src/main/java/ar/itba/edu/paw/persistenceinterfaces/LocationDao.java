package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import java.util.List;

public interface LocationDao {

    LocationImpl addLocation(LocationImpl lc);

    List<LocationImpl> getLocations(UserImpl user);

    LocationImpl getLocation(int location);

    LocationImpl editLocation(LocationImpl lc);

    void deleteLocation(LocationImpl lc);
}
