package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.EmailNotExistence;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.RepeatPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@RepeatPassword
public class RegisterForm {

    @NotEmpty
    @Size(min = 3, max = 100)
    @Email
    @EmailNotExistence
    private String email;

    @NotEmpty
    @Size(max = 100)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")
    /* contains at least one lowercase letter, one uppercase letter, one digit, and one special character, and has a minimum length of 8 characters */
    private String password;

    @NotEmpty
    @Size(max = 100)
    private String repeatPassword;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;

    @NotEmpty
    private String telephone;

}
