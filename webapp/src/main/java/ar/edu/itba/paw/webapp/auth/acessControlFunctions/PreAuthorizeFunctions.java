package ar.edu.itba.paw.webapp.auth.acessControlFunctions;

import ar.edu.itba.paw.exceptions.LendingNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetInstanceReviewsService;
import ar.edu.itba.paw.interfaces.UserReviewsService;
import ar.edu.itba.paw.webapp.form.UserReviewForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreAuthorizeFunctions {


    private final UserReviewsService userReviewsService;

    private final AssetInstanceReviewsService assetInstanceReviewsService;

    @Autowired
    public PreAuthorizeFunctions(UserReviewsService userReviewsService, AssetInstanceReviewsService assetInstanceReviewsService) {
        this.userReviewsService = userReviewsService;
        this.assetInstanceReviewsService = assetInstanceReviewsService;
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

    public boolean borrowerCanAssetInstanceReview(final int lendingId){
        try {
            return assetInstanceReviewsService.canReview(lendingId);
        }catch (LendingNotFoundException e){
            return false;
        }
    }
}
