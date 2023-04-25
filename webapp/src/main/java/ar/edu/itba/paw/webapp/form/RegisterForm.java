package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.EmailExistence;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterForm {

    @NotEmpty
    @Size(min = 3,max = 100)
    @Email
    @EmailExistence
    private String email;

    @NotEmpty
    @Size(max = 100)
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    /* contains at least one lowercase letter, one uppercase letter, one digit, and one special character, and has a minimum length of 8 characters */
    private String password;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String name;

}
