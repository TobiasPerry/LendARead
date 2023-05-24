package ar.edu.itba.paw.models.userContext.implementations;

import ar.edu.itba.paw.models.userContext.interfaces.Location;

import javax.persistence.*;

@Entity
@Table(name = "location")
final public class LocationImpl implements Location {
    @Column(length = 100, nullable = false)
    private String zipcode;
    @Column(length = 100, nullable = false)
    private String locality;
    @Column(length = 100, nullable = false)
    private String province;
    @Column(length = 100, nullable = false)
    private String country;

    @Column(length = 100)
    private String address = "Address";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
    @SequenceGenerator(sequenceName = "location_id_seq", name = "location_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    public LocationImpl(int id,String zipcode, String locality, String province, String country) {
        this.zipcode = zipcode;
        this.locality = locality;
        this.province = province;
        this.country = country;
        this.id = id;
    }
    public LocationImpl(String zipcode, String locality, String province, String country) {
        this.zipcode = zipcode;
        this.locality = locality;
        this.province = province;
        this.country = country;
    }
    public LocationImpl(){}

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
