package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;

public interface LocationDao {

    LocationImpl addLocation(LocationImpl lc);

}
