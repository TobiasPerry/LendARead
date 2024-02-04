package ar.edu.itba.paw.webapp.auth.acessControlFunctions;

import ar.edu.itba.paw.exceptions.*;
import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstanceReview;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccessFunctions {



    private final LocationsService locationsService;
    private final UserService userService;
    private final LendingService lendingService;

    private final AssetInstanceService assetInstanceService;

    private final AssetInstanceReviewsService assetInstanceReviewsService;


    @Autowired
    public AccessFunctions(UserService userService, LocationsService locationsService, LendingService lendingService, AssetInstanceService assetInstanceService, AssetInstanceReviewsService assetInstanceReviewsService) {
        this.userService = userService;
        this.locationsService = locationsService;
        this.lendingService = lendingService;
        this.assetInstanceService = assetInstanceService;
        this.assetInstanceReviewsService = assetInstanceReviewsService;
    }



    public boolean checkUser( int id) {
        try {
            User user = userService.getUserById(id);
            if (userService.getCurrentUser() == null)
                return false;
            return userService.getCurrentUser().getId() == user.getId();
        }catch (UserNotFoundException e){
            return true;
        }
    }
    public boolean locationOwner( Integer id) {
        try {
            if (userService.getCurrentUser() == null)
                return false;
            Optional<Location> location = locationsService.getLocation(id);
            if (!location.isPresent()) return true;
            return location.get().getUser().getEmail().equals(userService.getCurrentUser().getEmail());
        }catch (UserNotFoundException e) {
            return false;
        }
    }

    public boolean lendingLenderOrBorrower( Integer id)  {
        try {
            if (userService.getCurrentUser() == null)
                return false;
            return lendingService.getLender(id).getEmail().equals(userService.getCurrentUser().getEmail()) || lendingService.getBorrower(id).getEmail().equals(userService.getCurrentUser().getEmail());
        }catch (LendingNotFoundException e){
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
    public boolean assetInstanceOwner(Integer id){
        try {
            if  (userService.getCurrentUser() == null)
                return false;
            return assetInstanceService.isOwner(id, userService.getCurrentUser().getEmail());
        }catch (AssetInstanceNotFoundException e){
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
    public boolean assetInstanceReviewOwner(Integer idReview){
        try {
            if  (userService.getCurrentUser() == null)
                return false;
            Optional<AssetInstanceReview> review = assetInstanceReviewsService.getReviewById(idReview);
            if (!review.isPresent())
                return true;
            return  review.get().getReviewer().getEmail().equals(userService.getCurrentUser().getEmail());
        } catch (UserNotFoundException e) {
            return false;
        }
    }

}
