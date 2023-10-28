package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.form.ChangePasswordForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;

@Path("users")
@Component
public class UserController {
    @Autowired
    private UserService us;
    @Autowired
    private AssetExistanceService ais;
    @Context
    private UriInfo uriInfo;
    @Autowired
    private UserAssetInstanceService uais;

    @PATCH
    @Produces(value = { Vnd.VND_USER_CHANGE_PASSWORD })
    @Consumes(value = { Vnd.VND_USER_CHANGE_PASSWORD })
    public Response changePassword(@Valid  @NotEmpty final ChangePasswordForm changePasswordForm){
        us.changePassword(changePasswordForm.getPassword(),changePasswordForm.getToken());
        return Response.ok().build();
    }
    @POST
    @Produces(value = { Vnd.VND_USER })
    @Consumes(value = { Vnd.VND_USER })
    public Response createUser(@Valid @NotEmpty final RegisterForm registerForm) {
        final UserImpl user = us.createUser(registerForm.getEmail(),registerForm.getName(),registerForm.getTelephone(), registerForm.getPassword());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }
    @GET
    @Path("/{id}")
    @Produces(value = { Vnd.VND_USER })
    public Response getById(@PathParam("id") final long id) throws UserNotFoundException {
        final UserImpl user = us.getUserById(Math.toIntExact(id));
        if (user != null) {
            return Response.ok(UserDTO.fromUser(uriInfo,user)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @POST
    @Path("/reset-password-token")
    @Produces(value = { Vnd.VND_RESET_PASSWORD })
    @Consumes(value = { Vnd.VND_RESET_PASSWORD })
    public Response createChangePasswordToken() {
        us.createChangePasswordToken(us.getCurrentUser());
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/profilePic")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    public Response changeUserProfilePic(@PathParam("id") final long id, @Image @FormDataParam("image") final FormDataBodyPart image, @FormDataParam("image") byte[] imageBytes) throws UserNotFoundException {
        int photoId = us.changeUserProfilePic(Math.toIntExact(id),imageBytes);
        return Response.noContent().contentLocation(uriInfo.getRequestUri()).build();
    }

    @GET
    @Path("/{id}/profilePic")
    @Produces(value = {"image/webp"})
    public Response getUserProfilePic(@PathParam("id") final long id) throws UserNotFoundException {
        final UserImpl user = us.getUserById(Math.toIntExact(id));

        EntityTag eTag = new EntityTag(String.valueOf(user.getProfilePhoto()));

        byte[] profileImage =   user.getProfilePhoto().getPhoto();
        return Response.ok(profileImage).tag(eTag).build();
    }



}