package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.DateCheckValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class BorrowAssetForm {

    @DateCheckValidation
    LocalDate borrowDate;


    @DateCheckValidation
    LocalDate devolutionDate;

    @NotNull
    Integer assetInstanceId;
}
