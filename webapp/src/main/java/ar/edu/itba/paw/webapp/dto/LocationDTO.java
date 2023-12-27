package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.userContext.implementations.Location;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;

@Getter
@Setter
public class LocationDTO {

    private String name;
    private String address;
    private String zipcode;
    private String locality;
    private String province;
    private String country;
    private String userReference;

    private String selfUrl;

    public static LocationDTO fromLocation(UriInfo url, Location location) {
        LocationDTO dto = new LocationDTO();
        dto.name = location.getName();
        dto.address = location.getAddress();
        dto.zipcode = location.getZipcode();
        dto.locality = location.getLocality();
        dto.province = location.getProvince();
        dto.country = location.getCountry();
        dto.userReference = UserDTO.reference(url, location.getUser());
        dto.selfUrl = reference(url, location);
        return dto;
    }
    public static String reference(UriInfo url, Location location) {
        return url.getBaseUriBuilder().path("/api/locations").path(String.valueOf(location.getId())).build().toString();
    }

}

