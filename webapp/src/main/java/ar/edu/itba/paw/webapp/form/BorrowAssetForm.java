package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class BorrowAssetForm {

    @NotNull
    @DateTimeFormat
    LocalDate borrowDate;

    @NotNull
    @DateTimeFormat
    LocalDate devolutionDate;

    @NotNull
    Integer assetInstanceId;
}
