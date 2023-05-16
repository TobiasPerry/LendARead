package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.interfaces.Location;

import java.util.Optional;

public interface LocationDao {

    Location addLocation(Location lc);

}
