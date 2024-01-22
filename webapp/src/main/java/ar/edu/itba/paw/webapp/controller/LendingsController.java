package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.LendingService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.webapp.dto.LendingDTO;
import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import ar.edu.itba.paw.webapp.form.PatchLendingForm;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path(EndpointsUrl.Lendings_URL)
@Component
public class LendingsController {

    private final LendingService aas;

    private final UserService us;
    private final UserAssetInstanceService uais;
    private static final Logger LOGGER = LoggerFactory.getLogger(LendingsController.class);


    @Context
    private UriInfo uriInfo;

    @Autowired
    public LendingsController(final LendingService lendingService, final UserService userService, final UserAssetInstanceService userAssetInstanceService){
        this.aas = lendingService;
        this.us = userService;
        this.uais = userAssetInstanceService;
    }

    @GET
    @Produces(value = { Vnd.VND_ASSET_INSTANCE_LENDING })
    public Response getLendings(@QueryParam("page")@DefaultValue("1") Integer page,
                                @QueryParam("itemsPerPage")@DefaultValue("4") Integer itemsPerPage,
                                @QueryParam("assetInstanceId")  Integer assetInstanceId,
                                @QueryParam("borrowerId") Integer borrowerId,
                                @QueryParam("sort") @Pattern(regexp = "TITLE|LENDDATE|DEVOLUTIONDATE|BORROWER_USER|LENDER_USER|STATE",message = "{pattern.lendingSort}") String sort,
                                @QueryParam("sortDirection") @Pattern(regexp = "ASCENDING|DESCENDING",message = "{pattern.SortDirection}") String sortDirection,
                                @QueryParam("state") @Pattern(regexp = "DELIVERED|ACTIVE|FINISHED|REJECTED|CANCELED",message = "{lending.state.invalid}") String state,
                                @QueryParam("lenderId") Integer lenderId) {
        PagingImpl<Lending> paging = aas.getPagingActiveLendings(page, itemsPerPage, assetInstanceId, borrowerId, state == null?null:LendingState.fromString(state), lenderId, sort, sortDirection);
        List<LendingDTO> lendingDTOS = LendingDTO.fromLendings(paging.getList(), uriInfo);

        LOGGER.info("GET lendings/ page:{} itemsPerPage:{} assetInstanceId:{} borrowerId:{} state:{} lenderId:{}",page,itemsPerPage,assetInstanceId,borrowerId,state,lenderId);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<LendingDTO>>(lendingDTOS) {});
        PaginatedData.paginatedData(response, paging, uriInfo);
        return response.build();
    }
    @POST
    @Consumes(value = {Vnd.VND_ASSET_INSTANCE_LENDING})
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_LENDING})
    public Response addLending(@Valid  BorrowAssetForm borrowAssetForm) throws UserNotFoundException, AssetInstanceBorrowException, DayOutOfRangeException {
      Lending lending = aas.borrowAsset(borrowAssetForm.getAssetInstanceId(),us.getCurrentUser().getEmail(),borrowAssetForm.getBorrowDate(),borrowAssetForm.getDevolutionDate());
      LOGGER.info("POST lendings/ assetInstanceId:{}",borrowAssetForm.getAssetInstanceId());
      return Response.created(uriInfo.getRequestUriBuilder().path(String.valueOf(lending.getId())).build()).build();
    }
    @GET
    @Path("/{id}")
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_LENDING})
    public Response getLending(@PathParam("id") final int id) throws LendingNotFoundException {
        Lending lending = uais.getBorrowedAssetInstance(id);
        LOGGER.info("GET lendings/ id:{}",id);
        return Response.ok(LendingDTO.fromLending(lending, uriInfo)).build();
    }
    @GET
    @Path("/{id}/reviews")
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_LENDING})
    public Response getLendingReviews(@PathParam("id") final int id) throws LendingNotFoundException {
        Lending lending = uais.getBorrowedAssetInstance(id);
        LOGGER.info("GET lendings/ id:{}/reviews",id);
        return Response.ok(LendingDTO.fromLending(lending, uriInfo)).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(value = {Vnd.VND_ASSET_INSTANCE_LENDING_STATE})
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_LENDING_STATE})
    @PreAuthorize("@preAuthorizeFunctions.canChangeLendingStatus(#id,#patchLendingForm.state)")
    public Response editLending(@PathParam("id") final int id, @Valid  PatchLendingForm patchLendingForm) throws InvalidLendingStateTransitionException, UserNotFoundException, LendingNotFoundException {
        aas.changeLending(id, patchLendingForm.getState());
        LOGGER.info("PATCH lendings/ id:{} state:{}",id,patchLendingForm.getState());
        return Response.noContent().build();
    }

}
