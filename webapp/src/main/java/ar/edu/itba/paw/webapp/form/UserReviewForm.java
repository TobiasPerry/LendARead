package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserReviewForm {


    @Size(min = 1, max = 200)
    private String review;

    @Min(1)
    @Max(5)
    @NotNull
    private Integer rating;

    @NotNull
    private int lendingId;

}
