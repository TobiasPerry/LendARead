package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFunctions {


    private final UserReviewsService userReviewsService;

    private final UserService userService;

    @Autowired
    public AccessFunctions(UserReviewsService userReviewsService, UserService userService) {
        this.userReviewsService = userReviewsService;
        this.userService = userService;
    }




    public boolean borrowerCanReview(Integer id) throws UserNotFoundException, AssetInstanceNotFoundException, LendingNotFoundException {
        return userReviewsService.borrowerCanReview(id);
    }
    public boolean checkUser(HttpServletRequest request, String email) throws UserNotFoundException {
        return userService.getCurrentUser().equals(email);
    }
}
