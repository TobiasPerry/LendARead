package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserReview;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.webapp.form.SearchFilterSortForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
public class ReviewsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewsController.class);

    private final ViewResolver viewResolver;
    private final UserReviewsService userReviewsService;
    private final AssetInstanceService assetInstanceService;
    private final UserService userService;
    private final UserAssetInstanceService userAssetInstanceService;

    @Autowired
    public ReviewsController(
            @Qualifier("viewResolver") final ViewResolver viewResolver,
            final UserReviewsService userReviewsService,
            final AssetInstanceService assetInstanceService,
            final UserService userService,
            final UserAssetInstanceService userAssetInstanceService
            ){
        this.viewResolver = viewResolver;
        this.userReviewsService = userReviewsService;
        this.assetInstanceService = assetInstanceService;
        this.userService = userService;
        this.userAssetInstanceService = userAssetInstanceService;
    }

    @RequestMapping(value = "/review/lenderAdd", method = RequestMethod.POST)
    public ModelAndView reviewLender(
            final @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm
    ) throws AssetInstanceNotFoundException {

        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(reviewForm.getLendingId());

        final AssetInstanceImpl assetInstance = lending.getAssetInstance();
        final UserImpl user = lending.getUserReference();

        userReviewsService.addReview(new UserReview("Review desde controller", 4, user, assetInstance.getOwner()));

        return new ModelAndView("views/reviewSubmited");
    }

    @RequestMapping(value = "/review/lender/{lendingId}", method = RequestMethod.GET)
    public ModelAndView reviewLenderView(
            @PathVariable int lendingId,
            final @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm
            ) throws AssetInstanceNotFoundException{

        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);

        final AssetInstanceImpl assetInstance = lending.getAssetInstance();
        final UserImpl user = lending.getUserReference();

        ModelAndView mav = new ModelAndView("views/reviewLender");
        mav.addObject("book", assetInstance.getBook());
        mav.addObject("assetInstance", assetInstance);
        mav.addObject("user", user);
        mav.addObject("lendingId", lendingId);

        return mav;
    }

    @RequestMapping(value = "/review/borrowerAdd", method = RequestMethod.POST)
    public ModelAndView reviewBorrower(
            final @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm
    ) throws AssetInstanceNotFoundException {

        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(reviewForm.getLendingId());

        final AssetInstanceImpl assetInstance = lending.getAssetInstance();
        final UserImpl user = lending.getUserReference();

        userReviewsService.addReview(new UserReview("Review desde controller", 4, user, assetInstance.getOwner()));
        return new ModelAndView("views/reviewSubmited");
    }

    @RequestMapping(value = "/review/borrower/{lendingId}", method = RequestMethod.GET)
    public ModelAndView reviewBorrowerView(
            @PathVariable int lendingId,
            final @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm
            ) throws AssetInstanceNotFoundException{

        final LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);

        final AssetInstanceImpl assetInstance = lending.getAssetInstance();
        final UserImpl user = lending.getUserReference();

        ModelAndView mav = new ModelAndView("views/reviewBorrower");
        mav.addObject("book", assetInstance.getBook());
        mav.addObject("assetInstance", assetInstance);
        mav.addObject("user", user);
        mav.addObject("lendingId", lendingId);

        return mav;
    }

}
