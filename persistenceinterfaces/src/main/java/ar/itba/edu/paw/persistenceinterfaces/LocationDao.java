package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;

public interface LocationDao {

    LocationImpl addLocation(LocationImpl lc);

}
