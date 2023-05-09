package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
public class BorrowAssetForm {

    @DateTimeFormat
    String date;
}
