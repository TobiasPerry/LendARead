package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;

@Getter
@Setter
public class UserDTO {
    private String userName;
    private String email;

    private String image;

    private String password;

    private String telephone;
    public static UserDTO fromUser(UriInfo url, UserImpl user) {
        UserDTO dto = new UserDTO();
        dto.userName = user.getName();
        dto.telephone = user.getTelephone();
        dto.email = user.getEmail();
        dto.image = url.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).path("image").build().toString();
        return dto;

    }

}