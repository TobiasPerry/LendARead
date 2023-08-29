package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("users")
@Component
public class UserController {
    @Autowired
    private UserService us;
    @Context
    private UriInfo uriInfo;
  /*  @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listUsers() throws {
        final List<User> allUsers = us.getAll();
        return Response.ok(new UserList(allUsers)).build();
    }
    @POST
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response createUser(final UserDTO userDto) {
        final User user = us.register(userDto.getUsername(), userDto.getPassword());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }*/
    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final long id) throws UserNotFoundException {
        final UserImpl user = us.getUserById(Math.toIntExact(id));
        if (user != null) {
            return Response.ok(UserDTO.fromUser(uriInfo,user)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    /*@DELETE
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response deleteById(@PathParam("id") final long id) {
        us.deleteById(id);
        return Response.noContent().build();
    }*/
}