package ar.edu.itba.paw.webapp.auth.acessControlFunctions;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
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



    public boolean checkUser(HttpServletRequest request, int id) {
        try {
            return userService.getUser(userService.getCurrentUser()).getId() == id;
        }catch (UserNotFoundException e){
            return false;
        }
    }
    public boolean locationOwner(HttpServletRequest request, Integer id) {
        try {
            return locationsService.getLocation(id).getUser().getEmail().equals(userService.getCurrentUser());
        }catch (LocationNotFoundException e){
            return false;
        }
    }

    public boolean lendingOwner(HttpServletRequest request, Integer id)  {
        try {
            return assetAvailabilityService.getLender(id).getEmail().equals(userService.getCurrentUser());
        }catch (AssetInstanceNotFoundException e){
            return false;
        }
    }
    public boolean assetInstanceOwner(HttpServletRequest request, Integer id){
        try {
            return assetInstanceService.isOwner(id, userService.getCurrentUser());
        }catch (AssetInstanceNotFoundException e){
            return false;
        }
    }


}
