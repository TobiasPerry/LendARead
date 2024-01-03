package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.Location;
import ar.edu.itba.paw.models.userContext.implementations.User;

import java.util.List;

public interface LocationDao {

    Location addLocation(Location lc);

    List<Location> getLocations(User user);

    Location getLocation(int location);

    Location editLocation(Location lc);

}
