package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.User;
import ar.edu.itba.paw.models.userContext.UserReview;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.webapp.dto.UserDTO;
import ar.edu.itba.paw.webapp.dto.UserReviewsDTO;
import ar.edu.itba.paw.webapp.form.EditUserForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import ar.edu.itba.paw.webapp.form.ResetPasswordTokenForm;
import ar.edu.itba.paw.webapp.form.UserReviewForm;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.StaticCache;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path(EndpointsUrl.Users_URL)
@Component
public class UserController {
    private final UserService us;
    @Context
    private UriInfo uriInfo;

    private final UserReviewsService urs;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController (final UserService userService, final UserReviewsService userReviewsService){
        this.us = userService;
        this.urs = userReviewsService;

    }
    @PATCH
    @Path("/{id}")
    @Produces(value = { Vnd.VND_USER })
    @Consumes(value = { Vnd.VND_USER })
    public Response updateUser(@PathParam("id") final int id, @Valid final EditUserForm userUpdateForm) throws UserNotFoundException, UnableToChangeRoleException, ImageNotExistException {
        us.updateUser(id,userUpdateForm.getUsername(),userUpdateForm.getTelephone(),userUpdateForm.getRole(),userUpdateForm.getPassword(),userUpdateForm.getImageId());
        LOGGER.info("PUT user/ id:{}",id);
        return Response.noContent().build();
    }

    @POST
    @Produces(value = { Vnd.VND_USER })
    @Consumes(value = { Vnd.VND_USER })
    public Response createUser(@Valid  final RegisterForm registerForm) {
        final User user = us.createUser(registerForm.getEmail(),registerForm.getName(),registerForm.getTelephone(), registerForm.getPassword());
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId())).build();
        LOGGER.info("POST user/ email:{} name:{} telephone:{}",registerForm.getEmail(),registerForm.getName(),registerForm.getTelephone());
        return Response.created(uri).entity(UserDTO.fromUser(uriInfo,user)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(value = { Vnd.VND_USER })
    public Response getById(@Context javax.ws.rs.core.Request request,@PathParam("id") final int id) throws UserNotFoundException {
        final User user = us.getUserById(id);
        final UserDTO userDTO = UserDTO.fromUser(uriInfo,user);
        EntityTag eTag = new EntityTag(String.valueOf(userDTO.hashCode()));
        Response.ResponseBuilder response = StaticCache.getConditionalCacheResponse(request, eTag);
        if (response == null) {
            LOGGER.info("GET user/ id:{}",id);
            return Response.ok(userDTO).tag(eTag).build();
        }
        LOGGER.info("GET user/ id:{} 304",id);
        return response.build();
    }

    @POST
    @Produces(value = { Vnd.VND_RESET_PASSWORD })
    @Consumes(value = { Vnd.VND_RESET_PASSWORD })
    public Response createChangePasswordToken(@Valid ResetPasswordTokenForm passwordTokenForm) throws UnableToCreateTokenException {
        us.createChangePasswordToken(passwordTokenForm.getEmail());
        LOGGER.info("POST user/{}/reset-password-token",passwordTokenForm.getEmail());
        return Response.noContent().build();
    }


    @GET
    @Path("/{id}/lender_reviews")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response getLenderReviews(@PathParam("id") final int id,@QueryParam("page") @DefaultValue("1") final int page,@QueryParam("itemsPerPage")@DefaultValue("1") final int itemsPerPage) throws UserNotFoundException {
        PagingImpl<UserReview> items =urs.getUserReviewsAsLender(page,itemsPerPage,us.getUserById(id));
        if (items.getTotalPages() == 0 || items.getList().isEmpty()) {
            return Response.noContent().build();
        }
        List<UserReviewsDTO> reviewsDTOS = UserReviewsDTO.fromUserReviewsList(items.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserReviewsDTO>>(reviewsDTOS) {});
        LOGGER.info("GET user/{}/lender_reviews",id);
        PaginatedData.paginatedData(response,items,uriInfo);
        return response.build();

    }

    @GET
    @Path("/{id}/borrower_reviews")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response getBorrowerReviews(@PathParam("id") final int id,@QueryParam("page") @DefaultValue("1") final int page,@QueryParam("itemsPerPage")@DefaultValue("1") final int itemsPerPage) throws UserNotFoundException {
        PagingImpl<UserReview> items =urs.getUserReviewsAsBorrower(page,itemsPerPage,us.getUserById(id));
        if (items.getTotalPages() == 0 || items.getList().isEmpty()) {
            return Response.noContent().build();
        }
        List<UserReviewsDTO> reviewsDTOS = UserReviewsDTO.fromUserReviewsList(items.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserReviewsDTO>>(reviewsDTOS) {});
        LOGGER.info("GET user/{}/borrower_reviews",id);
        PaginatedData.paginatedData(response,items,uriInfo);
        return response.build();
    }

    @POST
    @Path("/{id}/lender_reviews")
    @PreAuthorize("@preAuthorizeFunctions.borrowerCanUserReview(#id,#lenderReviewForm)")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    @Consumes(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response createLenderReview(@PathParam("id") final int id,@Valid @RequestBody final UserReviewForm lenderReviewForm) throws UserNotFoundException, LendingNotFoundException, UnableToAddReviewException {
        UserReview userReview =urs.addReview(lenderReviewForm.getLendingId(),id,lenderReviewForm.getReview(),lenderReviewForm.getRating());
        final URI uri = uriInfo.getRequestUriBuilder().path(String.valueOf(userReview.getId())).build();
        LOGGER.info("POST user/{}/lender_reviews",id);
        return Response.created(uri).entity(UserReviewsDTO.fromUserReview(userReview,uriInfo)).build();
    }
    @POST
    @Path("/{id}/borrower_reviews")
    @PreAuthorize("@preAuthorizeFunctions.lenderCanUserReview(#id,#borrowerReviewForm)")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    @Consumes(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response createBorrowerReview(@PathParam("id") final int id,@Valid @RequestBody final UserReviewForm borrowerReviewForm) throws UserNotFoundException, LendingNotFoundException, UnableToAddReviewException {
        UserReview userReview = urs.addReview(borrowerReviewForm.getLendingId(),id,borrowerReviewForm.getReview(),borrowerReviewForm.getRating());
        final URI uri = uriInfo.getRequestUriBuilder().path(String.valueOf(userReview.getId())).build();
        LOGGER.info("POST user/{}/borrower_reviews",id);
        return Response.created(uri).entity(UserReviewsDTO.fromUserReview(userReview,uriInfo)).build();
    }

    @GET
    @Path("/{id}/lender_reviews/{reviewId}")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response getLenderReview(@PathParam("id") final int id,@PathParam("reviewId") final int reviewId) throws UserReviewNotFoundException, UserNotFoundException {
        UserReview userReview = urs.getUserReviewAsLender(id,reviewId);
        UserReviewsDTO userReviewsDTO = UserReviewsDTO.fromUserReview(userReview,uriInfo);
        LOGGER.info("GET user/{}/lender_reviews/{}",id,reviewId);
        return Response.ok( new GenericEntity<UserReviewsDTO>(userReviewsDTO){}).build();
    }
    @GET
    @Path("/{id}/borrower_reviews/{reviewId}")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response getBorrowerReview(@PathParam("id") final int id,@PathParam("reviewId") final int reviewId) throws UserReviewNotFoundException, UserNotFoundException {
        UserReview userReview = urs.getUserReviewAsBorrower(id,reviewId);
        UserReviewsDTO userReviewsDTO = UserReviewsDTO.fromUserReview(userReview,uriInfo);
        LOGGER.info("GET user/{}/borrower_reviews/{}",id,reviewId);
        return Response.ok( new GenericEntity<UserReviewsDTO>(userReviewsDTO){}).build();
    }
}