package ar.edu.itba.paw.models.userContext.factories;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;

final public class LocationFactory {
    public static LocationImpl createLocation(int id,String zipcode, String locality, String province, String country) {
        return new LocationImpl(id,zipcode, locality, province, country);
    }
    public static LocationImpl createLocation(String zipcode, String locality, String province, String country) {
        return new LocationImpl(zipcode, locality, province, country);
    }
}

