package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import javax.ws.rs.core.UriInfo;

public class UserDTO {
    private String userName;
    private String email;

    private String image;
    public static UserDTO fromUser(UriInfo url, UserImpl user) {
        UserDTO dto = new UserDTO();
        dto.userName = user.getName();
        dto.email = user.getEmail();
        dto.image = url.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).path("image").build().toString();
        return dto;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}