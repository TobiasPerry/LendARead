package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.DateCheckValidation;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.DayCheckOverlap;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@DayCheckOverlap
public class BorrowAssetForm {

    @DateCheckValidation
    LocalDate borrowDate;


    @DateCheckValidation
    LocalDate devolutionDate;

    @NotNull
    @Min(1)
    Integer assetInstanceId;
}
