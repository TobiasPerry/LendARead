package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.webapp.dto.LocationDTO;
import ar.edu.itba.paw.webapp.form.EditLocationForm;
import ar.edu.itba.paw.webapp.form.LocationForm;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Path("locations")
public class LocationsController {

    private final LocationsService ls;

    @Context
    private UriInfo uriInfo;
    @Autowired
    public LocationsController(final LocationsService ls) {
        this.ls = ls;
    }

    @GET
    @Path("/")
    @Produces(value = { Vnd.VND_LOCATION })
    public Response getLocation(@QueryParam("userId")@NotNull final Integer userId) throws UserNotFoundException {
        final List<LocationImpl> locations = ls.getLocationsById(userId);
        final List<LocationDTO> locationsDTO = new ArrayList<>();
        locations.forEach(location -> locationsDTO.add(LocationDTO.fromLocation(uriInfo, location)));
        return Response.ok(new GenericEntity<List<LocationDTO>>(locationsDTO){}).build();
    }
    @GET
    @Path("/{id}")
    @Produces(value = { Vnd.VND_LOCATION })
    public Response getLocationById(@PathParam("id") final Integer locationId) throws LocationNotFoundException {
        final LocationImpl location = ls.getLocation(locationId);
        return Response.ok(LocationDTO.fromLocation(uriInfo,location)).build();
    }
    @PATCH
    @Path("/{id}")
    @Produces(value = { Vnd.VND_LOCATION })
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA })
    public Response editLocation(@PathParam("id")final Integer locationId, @BeanParam @Valid EditLocationForm locationForm) throws LocationNotFoundException {
        LocationImpl location = ls.editLocationById(locationId, Optional.ofNullable(locationForm.getName()), Optional.ofNullable(locationForm.getLocality()), Optional.ofNullable(locationForm.getProvince()), Optional.ofNullable(locationForm.getCountry()), Optional.ofNullable(locationForm.getZipcode()));
        return Response.noContent().build();
    }
    @DELETE
    @Path("/{id}")
    @Produces(value = { Vnd.VND_LOCATION })
    public Response deleteLocation(@PathParam("id")final Integer locationId) throws LocationNotFoundException {
        ls.deleteLocationById(locationId);
        return Response.noContent().build();
    }
    @POST
    @Path("/")
    @Produces(value = { Vnd.VND_LOCATION })
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA })
    public Response addLocation(@BeanParam @Valid LocationForm locationForm) throws UserNotFoundException {
        LocationImpl location = ls.addLocation(locationForm.getName(), locationForm.getLocality(), locationForm.getProvince(), locationForm.getCountry(), locationForm.getZipcode());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(location.getId())).build();
        return Response.created(uri).build();    }
}
