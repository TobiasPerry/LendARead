package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.webapp.form.ChangeProfilePicForm;
import ar.edu.itba.paw.webapp.miscellaneous.FormFactoryAddAssetView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserProfileViewController {
    private final Logger LOGGER = LoggerFactory.getLogger(UserProfileViewController.class);
    private final UserService userService;
    private final UserReviewsService userReviewsService;
    private final Integer TOTAL_LATEST_REVIEWS = 3;
    private final String borrowerTab = "borrowerReviews";
    private final String lenderTab = "lenderReviews";

    public UserProfileViewController(UserService userService, UserReviewsService userReviewsService) {
        this.userService = userService;
        this.userReviewsService = userReviewsService;
    }

    @RequestMapping("/user/{id}")
    public ModelAndView userProfileView(@PathVariable(name = "id") final int id,
                                        @RequestParam(name = "activetab", defaultValue = borrowerTab) final String activeTab,
                                        @RequestParam(name = "activetabpage", defaultValue = "1") final int activeTabPage,
                                        @ModelAttribute("changeProfilePicForm") final ChangeProfilePicForm changeProfilePicForm) throws UserNotFoundException {
        UserImpl user = userService.getUserById(id);

        ModelAndView mav = new ModelAndView("/views/userProfileView")
                .addObject("user", user)
                .addObject("isCurrent", userService.isCurrent(id))
                .addObject("borrowerRating", userReviewsService.getRatingAsBorrower(user))
                .addObject("lenderRating", userReviewsService.getRatingAsLender(user))
                .addObject("activeTab", activeTab);

        if (activeTab.equals(borrowerTab)) {
            mav.addObject("lenderReviews", userReviewsService.getUserReviewsAsLender(1, this.TOTAL_LATEST_REVIEWS, user));
            mav.addObject("borrowerReviews", userReviewsService.getUserReviewsAsBorrower(activeTabPage, this.TOTAL_LATEST_REVIEWS, user));
        } else if (activeTab.equals(lenderTab)) {
            mav.addObject("lenderReviews", userReviewsService.getUserReviewsAsLender(activeTabPage, this.TOTAL_LATEST_REVIEWS, user));
            mav.addObject("borrowerReviews", userReviewsService.getUserReviewsAsBorrower(1, this.TOTAL_LATEST_REVIEWS, user));
        }

        return mav;
    }

    @RequestMapping(value = "/user/{id}/editPic", method = RequestMethod.POST)
    public ModelAndView changeProfilePic(@PathVariable(name = "id") final int id,
                                         @RequestParam(name = "file") final MultipartFile image,
                                         @Valid @ModelAttribute final ChangeProfilePicForm changeProfilePicForm,
                                         final BindingResult errors, HttpServletResponse response) throws InternalErrorException, UserNotFoundException {
        byte[] parsedImage = FormFactoryAddAssetView.getByteArray(image);

        if (errors.hasErrors() || parsedImage == null) {
            return userProfileView(id, "borrowerReviews", 1, changeProfilePicForm).addObject("INVALID_PHOTO", true);
        }

        try {
            userService.changeUserProfilePic(id, parsedImage);
            LOGGER.info("User {} has changed its profile image", id);
            return new ModelAndView("redirect:/user/" + id + "?editSuccess=true");
        } catch (UserNotFoundException exception) {
            LOGGER.error("Error changing profile picturo of user {}", id);
            return new ModelAndView("redirect:/user/" + id + "?editSuccess=false");
        }
    }

    @ModelAttribute
    public void addAttribute(final Model model) {
        model.addAttribute("path", "userView");
    }
}
