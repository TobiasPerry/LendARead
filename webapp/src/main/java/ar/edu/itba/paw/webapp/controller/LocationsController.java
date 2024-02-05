package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToDeleteLocationException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.webapp.dto.LocationDTO;
import ar.edu.itba.paw.webapp.form.EditLocationForm;
import ar.edu.itba.paw.webapp.form.LocationForm;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.StaticCache;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
@Path(EndpointsUrl.Locations_URL)
public class LocationsController {

    private final LocationsService ls;

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationsController.class);

    @Context
    private UriInfo uriInfo;
    @Autowired
    public LocationsController(final LocationsService ls) {
        this.ls = ls;
    }

    @GET
    @Produces(value = { Vnd.VND_LOCATION })
    @PreAuthorize("@preAuthorizeFunctions.isLocationOwner(#userId)")
    public Response getLocation(
            @QueryParam(value = "page")  @Min(1)  @DefaultValue("1") final Integer page,
            @QueryParam(value = "itemsPerPage")  @Min(1) @DefaultValue("10") final Integer itemsPerPage,
            @QueryParam("userId") @NotNull(message = "{userid.notNull}") final Integer userId
    ) {
        final PagingImpl<Location> locations = ls.getLocations(userId, page, itemsPerPage);
        if (locations.getTotalPages() == 0 || locations.getList().isEmpty()) {
            return Response.noContent().build();
        }
        LOGGER.info("GET location/ userId:{}",userId);
        List<LocationDTO> locationDTOS = LocationDTO.fromLocations(locations.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<LocationDTO>>(locationDTOS) {});
        PaginatedData.paginatedData(response, locations, uriInfo);
        return response.build();
    }
    @GET
    @Path("/{id}")
    @Produces(value = { Vnd.VND_LOCATION })
    public Response getLocationById(@Context javax.ws.rs.core.Request request,@PathParam("id") final Integer locationId) throws LocationNotFoundException {
        final Location location = ls.getLocation(locationId).orElseThrow(LocationNotFoundException::new);
        final LocationDTO locationDTO = LocationDTO.fromLocation(uriInfo,location);
        EntityTag eTag = new EntityTag(String.valueOf(locationDTO.hashCode()));
        Response.ResponseBuilder response = StaticCache.getConditionalCacheResponse(request, eTag);
        if (response == null) {
            LOGGER.info("GET location/ id:{}",locationId);
            return Response.ok(locationDTO).tag(eTag).build();
        }
        LOGGER.info("GET location/ id:{} 304",locationId);
        return response.build();
    }
    @PATCH
    @Path("/{id}")
    @Produces(value = { Vnd.VND_LOCATION })
    @Consumes(value = { Vnd.VND_LOCATION })
    public Response editLocation(@PathParam("id")final Integer locationId, @Valid EditLocationForm locationForm) throws LocationNotFoundException {
        ls.editLocationById(locationId, Optional.ofNullable(locationForm.getName()), Optional.ofNullable(locationForm.getLocality()), Optional.ofNullable(locationForm.getProvince()), Optional.ofNullable(locationForm.getCountry()), Optional.ofNullable(locationForm.getZipcode()));
        LOGGER.info("PATCH location/ id:{}",locationId);
        return Response.noContent().build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteLocation(@PathParam("id")final Integer locationId) throws LocationNotFoundException, UnableToDeleteLocationException {
        ls.deleteLocationById(locationId);
        LOGGER.info("DELETE location/ id:{}",locationId);
        return Response.noContent().build();
    }
    @POST
    @Consumes(value = { Vnd.VND_LOCATION })
    public Response addLocation(@Valid LocationForm locationForm) throws UserNotFoundException {
        Location location = ls.addLocation(locationForm.getName(), locationForm.getLocality(), locationForm.getProvince(), locationForm.getCountry(), locationForm.getZipcode());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(location.getId())).build();
        LOGGER.info("POST location/ id:{}",location.getId());
        return Response.created(uri).entity(LocationDTO.fromLocation(uriInfo,location)).build();
    }
}
