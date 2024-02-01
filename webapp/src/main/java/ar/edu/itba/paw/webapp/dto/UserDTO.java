package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;

@Getter
@Setter
public class UserDTO {
    private String userName;
    private String image;
    private String telephone;
    private String role;
    private float rating;
    private float ratingAsLender;
    private float ratingAsBorrower;
    private String selfUrl;
    public static UserDTO fromUser(UriInfo url, User user) {
        UserDTO dto = new UserDTO();
        dto.userName = user.getName();
        dto.telephone = user.getTelephone();
        dto.role = user.getBehavior().toString();
        dto.rating = user.getRating();
        dto.ratingAsBorrower = user.getRatingAsBorrower();
        dto.ratingAsLender = user.getRatingAsLender();
        dto.selfUrl = reference(url, user);
        if (user.getProfilePhoto() != null)
            dto.image = url.getBaseUriBuilder().path(EndpointsUrl.IMAGE_URL).path(String.valueOf(user.getProfilePhoto().getId())).build().toString();
        return dto;
    }
    public static String reference(UriInfo url, User user) {
        return url.getBaseUriBuilder().path(EndpointsUrl.Users_URL).path(String.valueOf(user.getId())).build().toString();
    }

}