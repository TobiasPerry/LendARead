package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.EmailNotExistence;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
public class RegisterForm {

    @NotNull
    @Size(min = 3, max = 100)
    @Email
    @EmailNotExistence
    private String email;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",message = "{Pattern.userForm.password}")
    /* contains at least one lowercase letter, one uppercase letter, one digit, and one special character, and has a minimum length of 8 characters */
    private String password;

    @NotNull
    @Size(max = 100)
    private String repeatPassword;

    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    private String telephone;

}
