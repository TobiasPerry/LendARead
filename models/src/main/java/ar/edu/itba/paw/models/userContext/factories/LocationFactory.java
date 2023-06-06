package ar.edu.itba.paw.models.userContext.factories;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

final public class LocationFactory {
    public static LocationImpl createLocation(String name, String zipcode, String locality, String province, String country, UserImpl user) {
        return new LocationImpl(name, zipcode, locality, province, country, user);
    }
}

