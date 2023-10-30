package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.Sort;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.webapp.dto.AssetsInstancesDTO;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.AssetInstanceForm;
import ar.edu.itba.paw.webapp.miscellaneous.FormFactoryAddAssetView;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Path("assetInstance")
public class AssetInstanceController {


    private final UserAssetInstanceService uais;
    private final AssetInstanceService ais;

    private final AssetExistanceService aes;

    private final LocationsService ls;

    private final UserService us;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public AssetInstanceController(final UserAssetInstanceService uais, final AssetInstanceService ais,final AssetExistanceService aes,final LocationsService ls,final UserService us) {
        this.uais = uais;
        this.ais = ais;
        this.aes = aes;
        this.ls = ls;
        this.us = us;
    }

    @GET
    @Path("/{id}")
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    public Response getUserAssetsInstances(@PathParam("id") final int id) throws AssetInstanceNotFoundException {
        final AssetInstanceImpl assetInstance = ais.getAssetInstance(id);
        AssetsInstancesDTO assetDTO = AssetsInstancesDTO.fromAssetInstance(uriInfo,assetInstance);
        return Response.ok(assetDTO).build();
    }
    @GET
    @Path("/{id}/image")
    @Produces(value = {"image/webp"})
    public Response getImage(@PathParam("id") final int id) throws AssetInstanceNotFoundException {
        final AssetInstanceImpl assetInstance = ais.getAssetInstance(id);

        EntityTag eTag = new EntityTag(String.valueOf(assetInstance.getImage()));

        byte[] profileImage =   assetInstance.getImage().getPhoto();
        return Response.ok(profileImage).tag(eTag).build();
    }

    @GET
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_SEARCH})
    public Response getAssetsInstances( @QueryParam("search") @Nullable @Size(min = 1, max = 100) String search,
                                         @QueryParam("physicalConditions")@Nullable  List<String> physicalConditions,
                                         @QueryParam("languages") @Nullable List<String> languages,
                                         @QueryParam("sort")  @Nullable @Pattern(regexp = "AUTHOR_NAME|TITLE_NAME|RECENT|DEFAULT") String sort,
                                         @QueryParam("sortDirection")  @Nullable @Pattern(regexp = "ASCENDING|DESCENDING|DEFAULT") String sortDirection,
                                         @QueryParam("page") @Nullable @DefaultValue("1")  @Min(1) int currentPage,
                                         @QueryParam("minRating") @Nullable @DefaultValue("1")@Min(1) @Max(5) int minRating,
                                         @QueryParam("maxRating") @Nullable @DefaultValue("5") @Min(1) @Max(5)int maxRating,
                                         @QueryParam("itemsPerPage")@Nullable @DefaultValue("1") int itemsPerPage,
                                        @QueryParam("userId")  @DefaultValue("-1") int userId) throws  InternalErrorException, LocationNotFoundException {
        Page page = ais.getAllAssetsInstances(
                currentPage, itemsPerPage,
                new SearchQueryImpl(
                        (languages != null) ? languages : new ArrayList<>(),
                        (physicalConditions != null) ? physicalConditions : new ArrayList<>(),
                        (search != null) ? search : "",
                        (sort != null) ? Sort.fromString(sort) : Sort.RECENT,
                        (sortDirection != null) ? SortDirection.fromString(sort) : SortDirection.DESCENDING,
                        minRating,
                        maxRating,
                        userId
                        )
        );
        List<AssetsInstancesDTO> assetsInstancesDTO = AssetsInstancesDTO.fromAssetInstanceList(uriInfo, page.getBooks());
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AssetsInstancesDTO>>(assetsInstancesDTO) {});
        PaginatedData.paginatedData(response, page, uriInfo);

        return response.build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    public Response createAssetInstance( @Valid @BeanParam final AddAssetForm assetInstanceForm) throws UserNotFoundException, InternalErrorException, LocationNotFoundException {
        AssetInstanceImpl assetInstance = aes.addAssetInstance(FormFactoryAddAssetView.createAssetInstance(assetInstanceForm, us.getUser(us.getCurrentUser()), ls.getLocation(assetInstanceForm.getLocationId())), assetInstanceForm.getImageBytes());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(assetInstance.getId())).build();
        return Response.created(uri).build();
    }

    @PATCH
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    @Path("/{id}")
    public Response updateAssetInstance(@PathParam("id") final int id, @Valid @BeanParam final AssetInstanceForm assetInstanceForm) throws  AssetInstanceNotFoundException, ImageNotFoundException, LocationNotFoundException {
        ais.changeAssetInstance(id, Optional.ofNullable(assetInstanceForm.getPhysicalCondition()!= null? PhysicalCondition.fromString(assetInstanceForm.getPhysicalCondition()):null), Optional.ofNullable(assetInstanceForm.getMaxDays()), Optional.ofNullable(assetInstanceForm.getLocationId()), assetInstanceForm.getImageBytes(), Optional.ofNullable(assetInstanceForm.getDescription()), Optional.ofNullable(assetInstanceForm.getIsReservable()));
        return Response.noContent().build();
    }

}
