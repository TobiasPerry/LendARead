package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.AssetInstanceReviewsService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.implementations.AssetInstanceSort;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.webapp.dto.AssetInstanceReviewDTO;
import ar.edu.itba.paw.webapp.dto.AssetsInstancesDTO;
import ar.edu.itba.paw.webapp.form.AssetInstanceForm;
import ar.edu.itba.paw.webapp.form.AssetInstancePatchForm;
import ar.edu.itba.paw.webapp.form.AssetInstanceReviewForm;
import ar.edu.itba.paw.webapp.miscellaneous.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Path(EndpointsUrl.AssetInstances_URL)
public class AssetInstanceController {


    private final AssetInstanceService ais;



    private final AssetInstanceReviewsService air;

    private static final Logger LOGGER = LoggerFactory.getLogger(AssetInstanceController.class);

    @Context
    private UriInfo uriInfo;

    @Autowired
    public AssetInstanceController(final AssetInstanceService ais,final AssetInstanceReviewsService air) {
        this.ais = ais;
        this.air = air;
    }

    @GET
    @Path("/{id}")
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    public Response getUserAssetsInstances(@PathParam("id") final int id) throws AssetInstanceNotFoundException {
        final AssetInstance assetInstance = ais.getAssetInstance(id);
        LOGGER.info("GET assetInstances/{}",id);
        AssetsInstancesDTO assetDTO = AssetsInstancesDTO.fromAssetInstance(uriInfo,assetInstance);
        Response.ResponseBuilder response = Response.ok(assetDTO);
        return response.build();
    }
    @GET
    @Path("/{id}/image")
    @Produces(value = {"image/webp"})
    public Response getImage(@PathParam("id") final int id,
                             @QueryParam("size")  @DefaultValue("FULL") @Pattern(regexp = ("FULL|CUADRADA|PORTADA"),message = "{Image.size.pattern}") final String size,
                             @Context javax.ws.rs.core.Request request) throws AssetInstanceNotFoundException, IOException {
        final AssetInstance assetInstance = ais.getAssetInstance(id);

        EntityTag eTag = new EntityTag(Arrays.hashCode(assetInstance.getImage().getPhoto()) + size);

        Response.ResponseBuilder response = StaticCache.getConditionalCacheResponse(request, eTag);
        LOGGER.info("GET assetInstances/{}/image",id);
        if (response == null) {
            ImagesSizes imagesSizes = ImagesSizes.valueOf(size);
            byte[] image = imagesSizes.resizeImage(assetInstance.getImage().getPhoto());
            Response.ResponseBuilder responseBuilder = Response.ok(image).tag(eTag);
            return responseBuilder.build();
        }

        return response.build();
    }

    @GET
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_SEARCH})
    @PreAuthorize("@preAuthorizeFunctions.searchPrivateAssetInstances(#userId,#status)")
    public Response getAssetsInstances( @QueryParam("search")  @Size(min = 1, max = 100) String search,
                                         @QueryParam("physicalConditions")  List<String> physicalConditions,
                                         @QueryParam("languages")  List<String> languages,
                                         @QueryParam("sort") @Pattern(regexp = "AUTHOR_NAME|TITLE|RECENT|LANGUAGE|STATE",message = "{pattern.sort.AssetInstance}") String sort,
                                         @QueryParam("sortDirection") @Pattern(regexp = "ASCENDING|DESCENDING",message = "{pattern.SortDirection}") String sortDirection,
                                         @QueryParam("page")  @DefaultValue("1")  @Min(1) int currentPage,
                                         @QueryParam("status") @DefaultValue("PUBLIC") @Pattern(regexp = "PUBLIC|PRIVATE",message = "{pattern.status}") String status,
                                         @QueryParam("minRating")  @DefaultValue("1")@Min(1) @Max(5) int minRating,
                                         @QueryParam("maxRating")  @DefaultValue("5") @Min(1) @Max(5)int maxRating,
                                         @QueryParam("itemsPerPage") @DefaultValue("10") int itemsPerPage,
                                        @QueryParam("userId")  @DefaultValue("-1") int userId)  {
        AbstractPage<AssetInstance> page = ais.getAllAssetsInstances(
                currentPage, itemsPerPage,
                new SearchQueryImpl(
                        (languages != null) ? languages : new ArrayList<>(),
                        (physicalConditions != null) ? physicalConditions : new ArrayList<>(),
                        (search != null) ? search : "",
                        (sort != null) ? AssetInstanceSort.fromString(sort) : AssetInstanceSort.RECENT,
                        (sortDirection != null) ? SortDirection.fromString(sortDirection) : SortDirection.DESCENDING,
                        minRating,
                        maxRating,
                        userId,
                        AssetState.fromString(status)
                        )
        );
        List<AssetsInstancesDTO> assetsInstancesDTO = AssetsInstancesDTO.fromAssetInstanceList(uriInfo, page.getList());
        LOGGER.info("GET assetInstances/ search:{} physicalConditions:{} languages:{} sort:{} sortDirection:{} page:{} itemsPerPage:{} minRating:{} maxRating:{} userId:{}",search,physicalConditions,languages,sort,sortDirection,currentPage,itemsPerPage,minRating,maxRating,userId);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AssetsInstancesDTO>>(assetsInstancesDTO) {});
        PaginatedData.paginatedData(response, page, uriInfo);
        return response.build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    public Response createAssetInstance(@Valid @BeanParam final AssetInstanceForm assetInstanceForm) throws UserNotFoundException, InternalErrorException, LocationNotFoundException, AssetNotFoundException {
        AssetInstance assetInstance = ais.addAssetInstance(PhysicalCondition.fromString(assetInstanceForm.getPhysicalCondition()),assetInstanceForm.getDescription(),assetInstanceForm.getMaxDays(),assetInstanceForm.getIsReservable(), AssetState.fromString(assetInstanceForm.getState()),assetInstanceForm.getLocationId(),assetInstanceForm.getAssetId(),assetInstanceForm.getImageBytes());
        LOGGER.info("POST assetInstances/ id:{}",assetInstance.getId());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(assetInstance.getId())).build();
        return Response.created(uri).build();
    }

    @PATCH
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = {Vnd.VND_ASSET_INSTANCE})
    @Path("/{id}")
    public Response updateAssetInstance(@PathParam("id") final int id, @Valid @BeanParam final AssetInstancePatchForm assetInstancePatchForm) throws  AssetInstanceNotFoundException, ImageNotFoundException, LocationNotFoundException {
        ais.changeAssetInstance(id, Optional.ofNullable(assetInstancePatchForm.getPhysicalCondition()!= null? PhysicalCondition.fromString(assetInstancePatchForm.getPhysicalCondition()):null), Optional.ofNullable(assetInstancePatchForm.getMaxDays()), Optional.ofNullable(assetInstancePatchForm.getLocationId()), assetInstancePatchForm.getImageBytes(), Optional.ofNullable(assetInstancePatchForm.getDescription()), Optional.ofNullable(assetInstancePatchForm.getIsReservable()), Optional.ofNullable(assetInstancePatchForm.getState()));
        LOGGER.info("PATCH assetInstances/ id:{}",id);
        return Response.noContent().build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteAssetInstance(@PathParam("id") final int id) throws AssetInstanceNotFoundException {
        ais.removeAssetInstance(id);
        LOGGER.info("DELETE assetInstances/ id:{}",id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/reviews")
    @Produces(value ={Vnd.VND_ASSET_INSTANCE_REVIEW})
    public Response getAssetInstanceReviews(@PathParam("id") final int id, @QueryParam("page")  @DefaultValue("1") final int page, @QueryParam("itemsPerPage") @DefaultValue("4") final int itemsPerPage) throws AssetInstanceNotFoundException {
        PagingImpl<AssetInstanceReview> reviews = air.getAssetInstanceReviewsById(page, itemsPerPage,id);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<AssetInstanceReviewDTO>>(AssetInstanceReviewDTO.fromAssetInstanceReviews(reviews.getList(),uriInfo)) {});
        PaginatedData.paginatedData(response, reviews, uriInfo);
        LOGGER.info("GET assetInstances/{}/reviews page:{} itemsPerPage:{}",id,page,itemsPerPage);
        return response.build();
    }
    @POST
    @Path("/{id}/reviews")
    @PreAuthorize("@preAuthorizeFunctions.borrowerCanAssetInstanceReview(#id,#assetInstanceReviewForm)")
    @Consumes(value = {Vnd.VND_ASSET_INSTANCE_REVIEW})
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_REVIEW})
    public Response createAssetInstanceReview(@PathParam("id") final int id, @Valid @RequestBody final AssetInstanceReviewForm assetInstanceReviewForm) throws AssetInstanceNotFoundException, UserNotFoundException, LendingNotFoundException {
        AssetInstanceReview review = air.addReview(id,assetInstanceReviewForm.getLendingId(),assetInstanceReviewForm.getReview(),assetInstanceReviewForm.getRating());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(review.getId())).build();
        LOGGER.info("POST assetInstances/{}/reviews id:{}",id,review.getId());
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}/reviews/{idReview}")
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_REVIEW})
    public Response getAssetInstanceReview(@PathParam("id") final int id, @PathParam("idReview") final int idReview) throws  AssetInstanceReviewNotFoundException {
        AssetInstanceReview review = air.getReviewById(idReview);
        LOGGER.info("GET assetInstances/{}/reviews/{}",id,idReview);
        return Response.ok(AssetInstanceReviewDTO.fromAssetInstanceReview(review,uriInfo)).build();
    }
    @DELETE
    @Path("/{id}/reviews/{idReview}")
    public Response deleteReviewById(@PathParam("id") final int id, @PathParam("idReview") final int idReview) throws AssetInstanceReviewNotFoundException {
        air.deleteReviewById(idReview);
        LOGGER.info("DELETE assetInstances/{}/reviews/{}",id,idReview);
        return Response.noContent().build();
    }
}
