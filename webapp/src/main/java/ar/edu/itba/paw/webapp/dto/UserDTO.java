package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.models.userContext.implementations.UserImpl;

import javax.ws.rs.core.UriInfo;

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
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setTelephone(String telephone){
        this.telephone = telephone;
    }
    public String getTelephone(){
        return this.telephone;
    }
}