package ar.edu.itba.paw.webapp.auth.acessControlFunctions;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceReviewsService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetLendingContext.implementations.Lending;
import ar.edu.itba.paw.models.userContext.implementations.User;
import ar.edu.itba.paw.webapp.form.AssetInstanceReviewForm;
import ar.edu.itba.paw.webapp.form.UserReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreAuthorizeFunctions {


    private final UserReviewsService userReviewsService;


    private final AssetInstanceReviewsService assetInstanceReviewsService;

    private final UserService userService;


    private final UserAssetInstanceService uais;

    @Autowired
    public PreAuthorizeFunctions(UserReviewsService userReviewsService, AssetInstanceReviewsService assetInstanceReviewsService, UserService userService, UserAssetInstanceService uais) {
        this.userReviewsService = userReviewsService;
        this.assetInstanceReviewsService = assetInstanceReviewsService;
        this.userService = userService;
        this.uais = uais;
    }


    public boolean borrowerCanUserReview(final int recipientId,final UserReviewForm userReviewForm) {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null)
                return false;
            return userReviewsService.borrowerCanReview(recipientId, userReviewForm.getLendingId());
        }catch (LendingNotFoundException | UserNotFoundException e){
            return true;
        }
    }

    public boolean lenderCanUserReview(final int recipientId,final UserReviewForm userReviewForm)  {
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null)
                return false;
            return userReviewsService.lenderCanReview(recipientId, userReviewForm.getLendingId());
        }catch (LendingNotFoundException | UserNotFoundException e){
            return true;
        }
    }

    public boolean borrowerCanAssetInstanceReview(final int assetInstanceId,final AssetInstanceReviewForm assetInstanceReview){
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null)
                return false;
            return assetInstanceReviewsService.canReview(assetInstanceId,Math.toIntExact(assetInstanceReview.getLendingId()));
        }catch (LendingNotFoundException | UserNotFoundException e){
            return true;
        }
    }
    public boolean searchPrivateAssetInstances(final int userid,final String status){
        try {
            if (status.equals("PUBLIC")) {
                return true;
            }
            return status.equals("PRIVATE") && userService.getCurrentUser().getId() == userid;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
    public boolean canChangeLendingStatus(final int lendingId,final String status){
        try {
            Lending lending = uais.getBorrowedAssetInstance(lendingId);
            switch (status){
                case "REJECTED":
                    return lending.getAssetInstance().getOwner().getEmail().equals(userService.getCurrentUser().getEmail());
                case "FINISHED":
                    return lending.getAssetInstance().getOwner().getEmail().equals(userService.getCurrentUser().getEmail());
                case "DELIVERED":
                    return lending.getAssetInstance().getOwner().getEmail().equals(userService.getCurrentUser().getEmail());
                case "CANCELED":
                    return lending.getUserReference().getEmail().equals(userService.getCurrentUser().getEmail());
                default:
                    return true;
            }
        } catch (LendingNotFoundException e) {
            return true;
        }catch (UserNotFoundException e){
            return false;
        }
    }

    public boolean canListLendings (final Integer lenderId,final Integer borrowerId){
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null)
                return false;
            if (lenderId != null){
                return lenderId == currentUser.getId();
            }
            if (borrowerId != null){
                return borrowerId == currentUser.getId() ;
            }

            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }
    public boolean isLocationOwner(final Integer userId){
        try {
            User currentUser = userService.getCurrentUser();
            if (currentUser == null)
                return false;
            return userId == currentUser.getId();
        } catch (UserNotFoundException e) {
            return false;
        }
    }

}
