package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dto.RootDTO;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.StaticCache;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Component
@Path(EndpointsUrl.ROOT_URL)
public class RootController {


    @GET
    @Produces(value = { Vnd.VND_ROOT })
    public Response getApiEndPoints() {
        Response.ResponseBuilder response = Response.ok(new RootDTO());
        StaticCache.setUnconditionalCache(response);
        return response.build();
    }
}
