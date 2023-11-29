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
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("users")
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
    @Path("/{email}")
    @Produces(value = { Vnd.VND_USER_CHANGE_PASSWORD })
    @Consumes(value = { Vnd.VND_USER_CHANGE_PASSWORD })
    public Response changePassword(@PathParam("email") String email, @Valid  @NotEmpty final ChangePasswordForm changePasswordForm){
        us.changePassword(email,changePasswordForm.getPassword(),changePasswordForm.getToken());
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
    @Path("/{email}")
    @Produces(value = { Vnd.VND_USER })
    public Response getById(@PathParam("email") final String email) throws UserNotFoundException {
        final UserImpl user = us.getUser(email);
        return Response.ok(UserDTO.fromUser(uriInfo,user)).build();
    }
    @POST
    @Path("/{email}/reset-password-token")
    @Produces(value = { Vnd.VND_RESET_PASSWORD })
    @Consumes(value = { Vnd.VND_RESET_PASSWORD })
    public Response createChangePasswordToken(@PathParam("email") final String email) throws UserNotFoundException {
        us.createChangePasswordToken(email);
        return Response.ok().build();
    }

    @PUT
    @Path("/{email}/profilePic")
    @Consumes(value = {MediaType.MULTIPART_FORM_DATA})
    public Response changeUserProfilePic(@PathParam("email") final String email, @Image @FormDataParam("image") final FormDataBodyPart image, @FormDataParam("image") byte[] imageBytes) throws UserNotFoundException {
        int photoId = us.changeUserProfilePic(email,imageBytes);
        return Response.noContent().build();
    }

    @GET
    @Path("/{email}/profilePic")
    @Produces(value = {"image/webp"})
    public Response getUserProfilePic(@PathParam("email") final String email) throws UserNotFoundException {
        final UserImpl user = us.getUser(email);

        EntityTag eTag = new EntityTag(String.valueOf(user.getProfilePhoto()));

        byte[] profileImage =   user.getProfilePhoto().getPhoto();
        return Response.ok(profileImage).tag(eTag).build();
    }

    @GET
    @Path("/{email}/lender_reviews")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response getLenderReviews(@PathParam("email") final String email,@QueryParam("page") @DefaultValue("1") final int page,@QueryParam("itemsPerPage")@DefaultValue("1") final int itemsPerPage) throws UserNotFoundException {
        PagingImpl<UserReview> items =urs.getUserReviewsAsLender(page,itemsPerPage,us.getUser(email));
        List<UserReviewsDTO> reviewsDTOS = UserReviewsDTO.fromUserReviewsList(items.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserReviewsDTO>>(reviewsDTOS) {});

        PaginatedData.paginatedData(response,items,uriInfo);
        return response.build();

    }

    @GET
    @Path("/{email}/borrower_reviews")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response getBorrowerReviews(@PathParam("email") final String email,@QueryParam("page") @DefaultValue("1") final int page,@QueryParam("itemsPerPage")@DefaultValue("1") final int itemsPerPage) throws UserNotFoundException {
        PagingImpl<UserReview> items =urs.getUserReviewsAsBorrower(page,itemsPerPage,us.getUser(email));
        List<UserReviewsDTO> reviewsDTOS = UserReviewsDTO.fromUserReviewsList(items.getList(),uriInfo);
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserReviewsDTO>>(reviewsDTOS) {});

        PaginatedData.paginatedData(response,items,uriInfo);
        return response.build();
    }

    @POST
    @Path("/{email}/lender_reviews")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    @Consumes(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response createLenderReview(@PathParam("email") final String email,@Valid @RequestBody final UserReviewForm lenderReviewForm) throws UserNotFoundException, AssetInstanceNotFoundException, LendingNotFoundException {
        UserReview userReview =urs.addReview(lenderReviewForm.getLendingId(),us.getCurrentUser(),email,lenderReviewForm.getReview(),lenderReviewForm.getRating());
        final URI uri = uriInfo.getRequestUriBuilder().path(String.valueOf(userReview.getId())).build();
        return Response.created(uri).build();
    }
    @POST
    @Path("/{email}/borrower_reviews")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    @Consumes(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response createBorrowerReview(@PathParam("email") final String email,@Valid @RequestBody final UserReviewForm borrowerReviewForm) throws UserNotFoundException, AssetInstanceNotFoundException, LendingNotFoundException {
        UserReview userReview = urs.addReview(borrowerReviewForm.getLendingId(),us.getCurrentUser(),email,borrowerReviewForm.getReview(),borrowerReviewForm.getRating());
        final URI uri = uriInfo.getRequestUriBuilder().path(String.valueOf(userReview.getId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/{email}/lender_reviews/{reviewId}")
    @Produces(value = { Vnd.VND_USER_LENDER_REVIEW})
    public Response getLenderReview(@PathParam("email") final String email,@PathParam("reviewId") final int reviewId) throws UserReviewNotFoundException, UserNotFoundException {
        UserReview userReview = urs.getUserReviewAsLender(email,reviewId);
        UserReviewsDTO userReviewsDTO = UserReviewsDTO.fromUserReview(userReview,uriInfo);
        return Response.ok( new GenericEntity<UserReviewsDTO>(userReviewsDTO){}).build();
    }
    @GET
    @Path("/{email}/borrower_reviews/{reviewId}")
    @Produces(value = { Vnd.VND_USER_BORROWER_REVIEW})
    public Response getBorrowerReview(@PathParam("email") final String email,@PathParam("reviewId") final int reviewId) throws UserReviewNotFoundException, UserNotFoundException {
        UserReview userReview = urs.getUserReviewAsBorrower(email,reviewId);
        UserReviewsDTO userReviewsDTO = UserReviewsDTO.fromUserReview(userReview,uriInfo);
        return Response.ok( new GenericEntity<UserReviewsDTO>(userReviewsDTO){}).build();
    }



}