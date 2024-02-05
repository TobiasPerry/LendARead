package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class LocationDTO {

    private String name;
    private String zipcode;
    private String locality;
    private String province;
    private String country;
    private String userReference;
    private String status;
    private String selfUrl;
    private Integer id;

    public static LocationDTO fromLocation(UriInfo url, Location location) {
        LocationDTO dto = new LocationDTO();
        dto.name = location.getName();
        dto.zipcode = location.getZipcode();
        dto.locality = location.getLocality();
        dto.province = location.getProvince();
        dto.status = location.isActive() ? "ACTIVE" : "DELETED";
        dto.country = location.getCountry();
        dto.userReference = UserDTO.reference(url, location.getUser());
        dto.selfUrl = reference(url, location);
        dto.id = location.getId();
        return dto;
    }
    public static String reference(UriInfo url, Location location) {
        return url.getBaseUriBuilder().path(EndpointsUrl.Locations_URL).path(String.valueOf(location.getId())).build().toString();
    }
    public static List<LocationDTO> fromLocations(List<Location> locations, UriInfo url) {
        return locations.stream().map(lending -> fromLocation(url,lending)).collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (locality != null ? locality.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (userReference != null ? userReference.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (selfUrl != null ? selfUrl.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}

