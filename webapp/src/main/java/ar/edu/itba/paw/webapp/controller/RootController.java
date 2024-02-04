package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dto.RootDTO;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
@Path(EndpointsUrl.ROOT_URL)
public class RootController {

    @Context
    private UriInfo uriInfo;
    @GET
    @Produces(value = { Vnd.VND_ROOT })
    public Response getApiEndPoints() {
        return Response.ok(RootDTO.fromRoot(uriInfo)).build();

    }
}
