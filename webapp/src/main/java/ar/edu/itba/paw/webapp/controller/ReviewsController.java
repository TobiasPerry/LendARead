package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.validation.Valid;

@Controller
public class ReviewsController {

    private final ViewResolver viewResolver;
    private final UserReviewsService userReviewsService;
    private final AssetInstanceService assetInstanceService;
    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddAssetViewController.class);


    @Autowired
    public ReviewsController(@Qualifier("viewResolver") final ViewResolver viewResolver, final UserReviewsService userReviewsService, final AssetInstanceService assetInstanceService, final UserService userService){
        this.viewResolver = viewResolver;
        this.userReviewsService = userReviewsService;
        this.assetInstanceService = assetInstanceService;
        this.userService = userService;
    }

    @RequestMapping("/review/lender/{lendingId}")
    public ModelAndView reviewLenderView(
            @PathVariable int lendingId,
            final @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm
            ) throws AssetInstanceNotFoundException{

        ModelAndView mav = new ModelAndView("views/reviewLender");

        return mav;
    }

    @RequestMapping("/review/borrower/{lendingId}")
    public ModelAndView reviewBorrowerView(
            @PathVariable int lendingId,
            final @Valid @ModelAttribute("reviewForm") ReviewForm reviewForm
            ) throws AssetInstanceNotFoundException{

        ModelAndView mav = new ModelAndView("views/reviewBorrower");
        mav.addObject("lendingId", lendingId);

        return mav;
    }

}
