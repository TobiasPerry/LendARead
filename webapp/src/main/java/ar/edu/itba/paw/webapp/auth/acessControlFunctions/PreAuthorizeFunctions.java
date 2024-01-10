package ar.edu.itba.paw.webapp.auth.acessControlFunctions;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceReviewsService;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceReview;
import ar.edu.itba.paw.webapp.form.UserReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreAuthorizeFunctions {


    private final UserReviewsService userReviewsService;

    private final AssetInstanceReviewsService assetInstanceReviewsService;

    private final UserService userService;

    @Autowired
    public PreAuthorizeFunctions(UserReviewsService userReviewsService, AssetInstanceReviewsService assetInstanceReviewsService, UserService userService) {
        this.userReviewsService = userReviewsService;
        this.assetInstanceReviewsService = assetInstanceReviewsService;
        this.userService = userService;
    }


    public boolean borrowerCanUserReview(final int recipientId,final UserReviewForm userReviewForm) {
        try {
            return userReviewsService.borrowerCanReview(recipientId, userReviewForm.getLendingId());
        }catch (LendingNotFoundException | UserNotFoundException e){
            return false;
        }
    }

    public boolean lenderCanUserReview(final int recipientId,final UserReviewForm userReviewForm)  {
        try {
            return userReviewsService.lenderCanReview(recipientId, userReviewForm.getLendingId());
        }catch (LendingNotFoundException | UserNotFoundException e){
            return false;
        }
    }

    public boolean borrowerCanAssetInstanceReview(final int assetInstanceId,final AssetInstanceReview assetInstanceReview){
        try {
            return assetInstanceReviewsService.canReview(assetInstanceId,Math.toIntExact(assetInstanceReview.getId()));
        }catch (LendingNotFoundException | UserNotFoundException e){
            return false;
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
}
