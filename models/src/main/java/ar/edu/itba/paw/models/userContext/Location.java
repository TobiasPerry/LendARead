package ar.edu.itba.paw.models.userContext;

import javax.persistence.*;

@Entity
@Table(name = "location")
final public class Location {

    @Column(length = 100, nullable = false)
    private boolean active;

    @Column(length = 100, nullable = false)
    private String zipcode;
    @Column(length = 100, nullable = false)
    private String locality;
    @Column(length = 100, nullable = false)
    private String province;
    @Column(length = 100, nullable = false)
    private String country;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    private User userReference;

    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100)
    private String address = "Address";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id_seq")
    @SequenceGenerator(sequenceName = "location_id_seq", name = "location_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    public Location(int id, String name, String zipcode, String locality, String province, String country, User user) {
        this.zipcode = zipcode;
        this.locality = locality;
        this.province = province;
        this.country = country;
        this.id = id;
        this.userReference = user;
        this.name = name;
        this.active = true;
    }
    public Location(String name, String zipcode, String locality, String province, String country, User user) {
        this.zipcode = zipcode;
        this.locality = locality;
        this.province = province;
        this.country = country;
        this.userReference = user;
        this.name = name;
        this.active = true;
    }
    public Location(){}



    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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

    public User getUser() {
        return userReference;
    }
    public void setUser(User user) {
        this.userReference = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return zipcode.equals(location.zipcode) && locality.equals(location.locality) && province.equals(location.province) && country.equals(location.country);
    }

    @Override
    public int hashCode() {
        int result = (active ? 1 : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUserReference(User userReference) {
        this.userReference = userReference;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
