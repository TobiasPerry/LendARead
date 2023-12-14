package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.exceptions.UserReviewNotFoundException;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.dto.UserReviewsDTO;
import ar.edu.itba.paw.webapp.form.ChangePasswordForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.UserReviewForm;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.StaticCache;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/api/users")
@Component
public class UserController {
    private final UserService us;
    private final AssetExistanceService ais;
    @Context
    private UriInfo uriInfo;

    private final UserReviewsService urs;

    @Autowired
    public UserController (final UserService userService, final AssetExistanceService assetExistanceService, final UserReviewsService userReviewsService){
        this.us = userService;
        this.ais = assetExistanceService;
        this.urs = userReviewsService;

    }


    @PATCH
    @Path("/{id}")
    @Produces(value = { Vnd.VND_USER_CHANGE_PASSWORD })
    @Consumes(value = { Vnd.VND_USER_CHANGE_PASSWORD })
    public Response changePassword(@PathParam("id") int id, @Valid  @NotEmpty final ChangePasswordForm changePasswordForm){
        us.changePassword(id,changePasswordForm.getPassword(),changePasswordForm.getToken());
        return Response.noContent().build();
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
    public Response getById(@PathParam("id") final int id) throws UserNotFoundException {
        final UserImpl user = us.getUserById(id);
        Response.ResponseBuilder response = Response.ok(UserDTO.fromUser(uriInfo,user));
        return response.build();
    }
    @POST
    @Path("/{id}/reset-password-token")
    @Produces(value = { Vnd.VND_RESET_PASSWORD })
    @Consumes(value = { Vnd.VND_RESET_PASSWORD })
    public Response createChangePasswordToken(@PathParam("id") final int id) throws UserNotFoundException {
        us.createChangePasswordToken(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/profilePic")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    public Response changeUserProfilePic(@PathParam("id") final int id, @Image @FormDataParam("image") final FormDataBodyPart image, @FormDataParam("image") byte[] imageBytes) throws UserNotFoundException {
        int photoId = us.changeUserProfilePic(id,imageBytes);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/profilePic")
    @Produces(value = {"image/webp"})
    public Response getUserProfilePic(@PathParam("id") final int id,@Context javax.ws.rs.core.Request request) throws UserNotFoundException {
        final UserImpl user = us.getUserById(id);
        EntityTag eTag = new EntityTag(String.valueOf(user.getProfilePhoto().getId()));

        Response.ResponseBuilder response = StaticCache.getConditionalCacheResponse(request, eTag);

        if (response == null) {
            Response.ResponseBuilder responseBuilder = Response.ok(user.getProfilePhoto().getPhoto()).tag(eTag);
            return responseBuilder.build();
        }

        return response.build();
    }

    @GET
    @Path("/{id}/lender_reviews")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response getLenderReviews(@PathParam("id") final int id,@QueryParam("page") @DefaultValue("1") final int page,@QueryParam("itemsPerPage")@DefaultValue("1") final int itemsPerPage) throws UserNotFoundException {
        PagingImpl<UserReview> items =urs.getUserReviewsAsLender(page,itemsPerPage,us.getUserById(id));
        List<UserReviewsDTO> reviewsDTOS = UserReviewsDTO.fromUserReviewsList(items.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserReviewsDTO>>(reviewsDTOS) {});

        PaginatedData.paginatedData(response,items,uriInfo);
        return response.build();

    }

    @GET
    @Path("/{id}/borrower_reviews")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response getBorrowerReviews(@PathParam("id") final int id,@QueryParam("page") @DefaultValue("1") final int page,@QueryParam("itemsPerPage")@DefaultValue("1") final int itemsPerPage) throws UserNotFoundException {
        PagingImpl<UserReview> items =urs.getUserReviewsAsBorrower(page,itemsPerPage,us.getUserById(id));
        List<UserReviewsDTO> reviewsDTOS = UserReviewsDTO.fromUserReviewsList(items.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserReviewsDTO>>(reviewsDTOS) {});

        PaginatedData.paginatedData(response,items,uriInfo);
        return response.build();
    }

    @POST
    @Path("/{id}/lender_reviews")
    @PreAuthorize("@preAuthorizeFunctions.borrowerCanUserReview(#id,#lenderReviewForm)")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    @Consumes(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response createLenderReview(@PathParam("id") final int id,@Valid @RequestBody final UserReviewForm lenderReviewForm) throws UserNotFoundException, AssetInstanceNotFoundException, LendingNotFoundException {
        UserReview userReview =urs.addReview(lenderReviewForm.getLendingId(),id,lenderReviewForm.getReview(),lenderReviewForm.getRating());
        final URI uri = uriInfo.getRequestUriBuilder().path(String.valueOf(userReview.getId())).build();
        return Response.created(uri).build();
    }
    @POST
    @Path("/{id}/borrower_reviews")
    @PreAuthorize("@preAuthorizeFunctions.lenderCanUserReview(#id,#borrowerReviewForm)")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    @Consumes(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response createBorrowerReview(@PathParam("id") final int id,@Valid @RequestBody final UserReviewForm borrowerReviewForm) throws UserNotFoundException, AssetInstanceNotFoundException, LendingNotFoundException {
        UserReview userReview = urs.addReview(borrowerReviewForm.getLendingId(),id,borrowerReviewForm.getReview(),borrowerReviewForm.getRating());
        final URI uri = uriInfo.getRequestUriBuilder().path(String.valueOf(userReview.getId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}/lender_reviews/{reviewId}")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response getLenderReview(@PathParam("id") final int id,@PathParam("reviewId") final int reviewId) throws UserReviewNotFoundException, UserNotFoundException {
        UserReview userReview = urs.getUserReviewAsLender(id,reviewId);
        UserReviewsDTO userReviewsDTO = UserReviewsDTO.fromUserReview(userReview,uriInfo);
        return Response.ok( new GenericEntity<UserReviewsDTO>(userReviewsDTO){}).build();
    }
    @GET
    @Path("/{id}/borrower_reviews/{reviewId}")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response getBorrowerReview(@PathParam("id") final int id,@PathParam("reviewId") final int reviewId) throws UserReviewNotFoundException, UserNotFoundException {
        UserReview userReview = urs.getUserReviewAsBorrower(id,reviewId);
        UserReviewsDTO userReviewsDTO = UserReviewsDTO.fromUserReview(userReview,uriInfo);
        return Response.ok( new GenericEntity<UserReviewsDTO>(userReviewsDTO){}).build();
    }



}