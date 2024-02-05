package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.models.userContext.User;
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
    private Integer id;
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
        dto.id = user.getId();
        return dto;
    }
    public static String reference(UriInfo url, User user) {
        return url.getBaseUriBuilder().path(EndpointsUrl.Users_URL).path(String.valueOf(user.getId())).build().toString();
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        result = 31 * result + (ratingAsLender != +0.0f ? Float.floatToIntBits(ratingAsLender) : 0);
        result = 31 * result + (ratingAsBorrower != +0.0f ? Float.floatToIntBits(ratingAsBorrower) : 0);
        result = 31 * result + (selfUrl != null ? selfUrl.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}