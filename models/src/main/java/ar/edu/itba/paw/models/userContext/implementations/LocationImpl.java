package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.userContext.interfaces.Location;

final public class LocationImpl implements Location {
    private final String zipcode;
    private final String locality;
    private final String province;
    private final String country;

    private final int id;
    public LocationImpl(int id,String zipcode, String locality, String province, String country) {
        this.zipcode = zipcode;
        this.locality = locality;
        this.province = province;
        this.country = country;
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", zipcode, locality, province, country) ;
    }

    @Override
    public String getZipcode() {
        return zipcode;
    }

    @Override
    public String getLocality() {
        return locality;
    }

    @Override
    public String getProvince() {
        return province;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getAddress() {
        return null;
    }
}
