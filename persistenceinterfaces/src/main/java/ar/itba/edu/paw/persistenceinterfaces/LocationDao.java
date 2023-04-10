package ar.itba.edu.paw.persistenceinterfaces;

import models.userContext.interfaces.Location;

import java.util.Optional;

public interface LocationDao {

    Optional<Integer> addLocation(Location lc);

}
