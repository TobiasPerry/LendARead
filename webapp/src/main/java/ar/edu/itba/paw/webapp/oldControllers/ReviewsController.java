package ar.edu.itba.paw.webapp.oldControllers;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UnauthorizedUserException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.webapp.form.BorrowerReviewForm;
import ar.edu.itba.paw.webapp.form.LenderReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.validation.Valid;

@Controller
public class ReviewsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewsController.class);

    private final ViewResolver viewResolver;
    private final UserReviewsService userReviewsService;
    private final AssetInstanceReviewsService assetInstanceReviewsService;
    private final AssetInstanceService assetInstanceService;
    private final UserService userService;
    private final UserAssetInstanceService userAssetInstanceService;

    @Autowired
    public ReviewsController(
            @Qualifier("viewResolver") final ViewResolver viewResolver,
            final UserReviewsService userReviewsService,
            final AssetInstanceService assetInstanceService,
            final UserService userService,
            final UserAssetInstanceService userAssetInstanceService,
            final AssetInstanceReviewsService assetInstanceReviewsService
            ){
        this.viewResolver = viewResolver;
        this.userReviewsService = userReviewsService;
        this.assetInstanceService = assetInstanceService;
        this.userService = userService;
        this.userAssetInstanceService = userAssetInstanceService;
        this.assetInstanceReviewsService = assetInstanceReviewsService;
    }

    @RequestMapping(value = "/review/lenderAdd/{lendingId}", method = RequestMethod.POST)
    public ModelAndView reviewLender(
            @PathVariable int lendingId,
            final @Valid @ModelAttribute("reviewForm") LenderReviewForm reviewForm,
            final BindingResult errors
    ) throws AssetInstanceNotFoundException, UnauthorizedUserException {

        if(errors.hasErrors())
            return reviewLenderView(lendingId, true, reviewForm);

        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);

        final AssetInstanceImpl assetInstance = lending.getAssetInstance();
        final UserImpl user = lending.getUserReference();

        userReviewsService.addReview(new UserReview(reviewForm.getReview(), reviewForm.getRating(), assetInstance.getOwner(), user,lending));

        return new ModelAndView("redirect:/lentBookDetails/"+ lending.getId());
    }

    @RequestMapping(value = "/review/lender/{lendingId}", method = RequestMethod.GET)
    public ModelAndView reviewLenderView(
            @PathVariable int lendingId,
            @RequestParam(name = "hasErrors", required = false, defaultValue = "false") boolean hasErrors,
            final @Valid @ModelAttribute("reviewForm") LenderReviewForm reviewForm
            ) throws AssetInstanceNotFoundException, UnauthorizedUserException {

        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);

        final AssetInstanceImpl assetInstance = lending.getAssetInstance();
        final UserImpl user = lending.getUserReference();

        if(!assetInstanceService.isOwner(assetInstance, userService.getCurrentUser()))
            throw new UnauthorizedUserException("Current user is not owner of the book");

        ModelAndView mav = new ModelAndView("views/reviewLender");
        mav.addObject("book", assetInstance.getBook());
        mav.addObject("assetInstance", assetInstance);
        mav.addObject("user", user);
        mav.addObject("lendingId", lendingId);

        mav.addObject("reviewError", hasErrors && reviewForm.getReview() != null);
        mav.addObject("ratingError", hasErrors && reviewForm.getRating() != null);


        return mav;
    }

    @RequestMapping(value = "/review/borrowerAdd/{lendingId}", method = RequestMethod.POST)
    public ModelAndView reviewBorrower(
            @PathVariable int lendingId,
            final @Valid @ModelAttribute("reviewForm") BorrowerReviewForm reviewForm,
            final BindingResult errors
    ) throws AssetInstanceNotFoundException, UserNotFoundException, UnauthorizedUserException {

        if(errors.hasErrors())
            return reviewBorrowerView(lendingId, true, reviewForm);

        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);

        final UserImpl user = lending.getUserReference();

        assetInstanceReviewsService.addReview(new AssetInstanceReview(lending, reviewForm.getAssetInstanceReview(),user, reviewForm.getAssetInstanceRating()));
        userReviewsService.addReview(new UserReview(reviewForm.getUserReview(), reviewForm.getUserRating(), user, lending.getAssetInstance().getOwner(),lending));

        return new ModelAndView("redirect:/borrowedBookDetails/" + lendingId);
    }

    @RequestMapping(value = "/review/borrower/{lendingId}", method = RequestMethod.GET)
    public ModelAndView reviewBorrowerView(
            @PathVariable int lendingId,
            @RequestParam(name = "hasErrors", required = false, defaultValue = "false") boolean hasErrors,
            final @Valid @ModelAttribute("reviewForm") BorrowerReviewForm reviewForm
            ) throws AssetInstanceNotFoundException, UnauthorizedUserException, UserNotFoundException {

        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);

        final AssetInstanceImpl assetInstance = lending.getAssetInstance();
        final UserImpl user = lending.getUserReference();

        if(!user.equals(userService.getUser(userService.getCurrentUser())))
            throw new UnauthorizedUserException("User has not borrowed this book");

        ModelAndView mav = new ModelAndView("views/reviewBorrower");
        mav.addObject("book", assetInstance.getBook());
        mav.addObject("assetInstance", assetInstance);
        mav.addObject("user", user);
        mav.addObject("lendingId", lendingId);

        mav.addObject("reviewUserError", hasErrors && reviewForm.getUserReview() != null);
        mav.addObject("ratingUserError", hasErrors && reviewForm.getUserRating() != null);
        mav.addObject("reviewAssetInstanceError", hasErrors && reviewForm.getAssetInstanceReview() != null);
        mav.addObject("ratingAssetInstanceError", hasErrors && reviewForm.getAssetInstanceRating() != null);

        return mav;
    }

}
