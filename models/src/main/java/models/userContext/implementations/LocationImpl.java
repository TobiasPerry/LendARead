package models.userContext.implementations;

import models.userContext.interfaces.Location;

public class LocationImpl implements Location {
        private final String zipcode;
        private final String locality;
        private final String province;
        private final String country;

    public LocationImpl(String zipcode, String locality, String province, String country) {
        this.zipcode = zipcode;
        this.locality = locality;
        this.province = province;
        this.country = country;
    }
}
