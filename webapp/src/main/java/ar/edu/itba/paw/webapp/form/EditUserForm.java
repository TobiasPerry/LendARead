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

    @Size(max = 100)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",message = "{Pattern.userForm.password}")
    /* contains at least one lowercase letter, one uppercase letter, one digit, and one special character, and has a minimum length of 8 characters */
    private String password;

    private Integer imageId;
}
