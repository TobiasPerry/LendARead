package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;

@Getter
@Setter
public class LocationDTO {

    private int id;
    private String name;
    private String address;
    private String zipcode;
    private String locality;
    private String province;
    private String country;
    private String userReference;
    private boolean active;

    public static LocationDTO fromLocation(UriInfo url, LocationImpl location) {
        LocationDTO dto = new LocationDTO();
        dto.id = location.getId();
        dto.name = location.getName();
        dto.address = location.getAddress();
        dto.zipcode = location.getZipcode();
        dto.locality = location.getLocality();
        dto.province = location.getProvince();
        dto.country = location.getCountry();
        dto.userReference = UserDTO.reference(url, location.getUser());
        dto.active = location.isActive();
        return dto;
    }
    public static String reference(UriInfo url, LocationImpl location) {
        return url.getBaseUriBuilder().path("locations").path(String.valueOf(location.getId())).build().toString();
    }

}

