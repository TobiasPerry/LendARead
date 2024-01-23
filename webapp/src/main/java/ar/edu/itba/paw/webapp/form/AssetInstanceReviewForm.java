package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AssetInstanceReviewForm {

    @Size(min = 1,max = 200)
    @NotNull
    private String review;


    @Min(1)
    @Max(5)
    @NotNull
    private Integer rating;


    @NotNull
    @Min(1)
    private int lendingId;



}
