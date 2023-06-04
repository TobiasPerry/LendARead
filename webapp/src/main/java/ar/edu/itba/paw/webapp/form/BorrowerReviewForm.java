package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BorrowerReviewForm {

    @Size(max = 200)
    private String assetInstanceReview;

    @Size(max = 200)
    private String userReview;

    @Min(1)
    @Max(5)
    private int userRating;

    @Min(1)
    @Max(5)
    private int assetInstanceRating;

    private int lendingId;

    public BorrowerReviewForm(){
        this.assetInstanceRating = 1;
        this.userRating = 1;
    }

}
