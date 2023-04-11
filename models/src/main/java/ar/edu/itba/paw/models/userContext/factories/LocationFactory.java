package ar.edu.itba.paw.models.userContext.factories;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;

final public class LocationFactory {
    private LocationFactory() {}
    public static Location createLocation(String zipcode, String locality, String province, String country) {
        return new LocationImpl(zipcode, locality, province, country);
    }
}

