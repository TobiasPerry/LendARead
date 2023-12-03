package ar.edu.itba.paw.webapp.auth.acessControlFunctions;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.LocationNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFunctions {


    private final UserReviewsService userReviewsService;

    private final LocationsService locationsService;
    private final UserService userService;
    private final AssetAvailabilityService assetAvailabilityService;

    private final AssetInstanceService assetInstanceService;

    @Autowired
    public AccessFunctions(UserReviewsService userReviewsService, UserService userService, LocationsService locationsService, AssetAvailabilityService assetAvailabilityService, AssetInstanceService assetInstanceService) {
        this.userReviewsService = userReviewsService;
        this.userService = userService;
        this.locationsService = locationsService;
        this.assetAvailabilityService = assetAvailabilityService;
        this.assetInstanceService = assetInstanceService;
    }




    public boolean borrowerCanReview(Integer id) throws UserNotFoundException, AssetInstanceNotFoundException, LendingNotFoundException {
        return userReviewsService.borrowerCanReview(id);
    }
    public boolean checkUser(HttpServletRequest request, String email) throws UserNotFoundException {
        return userService.getCurrentUser().equals(email);
    }
    public boolean locationOwner(HttpServletRequest request, Integer id) throws LocationNotFoundException {
        return locationsService.getLocation(id).getUser().getEmail().equals(userService.getCurrentUser());
    }
    public boolean lendingOwner(HttpServletRequest request, Integer id) throws AssetInstanceNotFoundException {
        return assetAvailabilityService.getLender(id).getEmail().equals(userService.getCurrentUser());
    }
    public boolean assetInstanceOwner(HttpServletRequest request, Integer id) throws AssetInstanceNotFoundException {
        return assetInstanceService.isOwner(id, userService.getCurrentUser());
    }


}
