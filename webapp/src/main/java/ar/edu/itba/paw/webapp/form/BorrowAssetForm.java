package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class BorrowAssetForm {

    @Length(min = 1, max = 11)
    String borrowDate;

    @Length(min = 1, max = 11)
    String devolutionDate;
}
