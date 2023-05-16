package ar.edu.itba.paw.models.userContext.factories;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.interfaces.Location;

final public class LocationFactory {
    public static Location createLocation(int id,String zipcode, String locality, String province, String country) {
        return new LocationImpl(id,zipcode, locality, province, country);
    }
}

