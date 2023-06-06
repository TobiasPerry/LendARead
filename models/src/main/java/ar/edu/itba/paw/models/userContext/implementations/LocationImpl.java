package ar.edu.itba.paw.models.userContext.implementations;

import javax.persistence.*;

@Entity
@Table(name = "location")
final public class LocationImpl{
    @Column(length = 100, nullable = false)
    private String zipcode;
    @Column(length = 100, nullable = false)
    private String locality;
    @Column(length = 100, nullable = false)
    private String province;
    @Column(length = 100, nullable = false)
    private String country;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    private  UserImpl userReference;
//    @Column(length = 100, nullable = false, unique = true)
//    private String name;
    @Column(length = 100)
    private String address = "Address";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
    @SequenceGenerator(sequenceName = "location_id_seq", name = "location_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    public LocationImpl(int id,String name, String zipcode, String locality, String province, String country, UserImpl user) {
        this.zipcode = zipcode;
        this.locality = locality;
        this.province = province;
        this.country = country;
        this.id = id;
        this.userReference = user;
    }
    public LocationImpl(String name, String zipcode, String locality, String province, String country) {
        this.zipcode = zipcode;
        this.locality = locality;
        this.province = province;
        this.country = country;
    }
    public LocationImpl(){}

    public int getId() {
        return id;
    }

    public String toString() {
        return String.format("%s, %s, %s, %s", zipcode, locality, province, country) ;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getLocality() {
        return locality;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationImpl location = (LocationImpl) o;
        return zipcode.equals(location.zipcode) && locality.equals(location.locality) && province.equals(location.province) && country.equals(location.country);
    }

}
