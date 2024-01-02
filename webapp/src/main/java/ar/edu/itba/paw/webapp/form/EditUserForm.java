package ar.edu.itba.paw.webapp.form;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EditUserForm {


    @Size(max = 250)
    private String username;


    @Size(max = 30)
    private String telephone;


    @Pattern(regexp = "LENDER|BORROWER", message = "{Invalid.role}")
    private String role;
}
