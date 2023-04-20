package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;

import javax.validation.constraints.Size;

public class LoginForm {

    @NotEmpty
    @Size(min = 3,max = 100)
    @Email
    private String email;

    @NotEmpty
    @Size(max = 100)
    private String password;
}
