package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingState;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.webapp.dto.LendingDTO;
import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import ar.edu.itba.paw.webapp.form.PatchLendingForm;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/api/lendings")
@Controller
public class LendingsController {

    private final AssetAvailabilityService aas;

    private final UserService us;
    private final UserAssetInstanceService uais;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public LendingsController(final AssetAvailabilityService assetAvailabilityService, final UserService userService, final UserAssetInstanceService userAssetInstanceService){
        this.aas = assetAvailabilityService;
        this.us = userService;
        this.uais = userAssetInstanceService;
    }

    @GET
    @Path("/")
    @Produces(value = { Vnd.VND_ASSET_INSTANCE_LENDING })
    public Response getLendings(@QueryParam("page")@DefaultValue("1") Integer page,
                                @QueryParam("itemsPerPage")@DefaultValue("1") Integer itemsPerPage,
                                @QueryParam("assetInstanceId") @Nullable Integer assetInstanceId,
                                @QueryParam("borrowerId")@Nullable Integer borrowerId,
                                @QueryParam("state")@Nullable @Pattern(regexp = "DELIVERED|ACTIVE|FINISHED|REJECTED") String state,
                                @QueryParam("lenderId")@Nullable Integer lenderId) {
        PagingImpl<LendingImpl> paging = aas.getPagingActiveLendings(page, itemsPerPage, assetInstanceId, borrowerId, state == null?null:LendingState.fromString(state), lenderId);
        List<LendingDTO> lendingDTOS = LendingDTO.fromLendings(paging.getList(), uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<LendingDTO>>(lendingDTOS) {});
        PaginatedData.paginatedData(response, paging, uriInfo);
        return response.build();
    }
    @POST
    @Path("/")
    @Consumes(value = {Vnd.VND_ASSET_INSTANCE_LENDING})
    public Response addLending(@Valid  BorrowAssetForm borrowAssetForm) throws UserNotFoundException, AssetInstanceBorrowException, DayOutOfRangeException {
      LendingImpl lending = aas.borrowAsset(borrowAssetForm.getAssetInstanceId(),us.getCurrentUser(),borrowAssetForm.getBorrowDate(),borrowAssetForm.getDevolutionDate());
      return Response.created(uriInfo.getRequestUriBuilder().path(String.valueOf(lending.getId())).build()).build();
    }
    @GET
    @Path("/{id}")
    @Produces(value = {Vnd.VND_ASSET_INSTANCE_LENDING})
    public Response getLending(@PathParam("id") final int id) throws LendingNotFoundException {
        LendingImpl lending = uais.getBorrowedAssetInstance(id);
        return Response.ok(LendingDTO.fromLending(lending, uriInfo)).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(value = {Vnd.VND_ASSET_INSTANCE_LENDING_STATE})
    public Response editLending(@PathParam("id") final int id, @Valid  PatchLendingForm patchLendingForm) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        aas.changeLending(id, patchLendingForm.getState());
        return Response.noContent().build();
    }

}
