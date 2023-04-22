package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;

public class RegisterForm {

    @NotEmpty
    @Size(min = 3,max = 100)
    @Email
    private String email;

    @NotEmpty
    @Size(max = 100)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    /* contains at least one lowercase letter, one uppercase letter, one digit, and one special character, and has a minimum length of 8 characters */
    private String password;

    @NotEmpty
    @Size(min = 3,max = 100)
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
