package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.ValidToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Size;

@Getter @Setter
public class ChangePasswordForm {

    @ValidToken
    private String token;

    @NotEmpty
    @Size(max = 100)
    //  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    /* contains at least one lowercase letter, one uppercase letter, one digit, and one special character, and has a minimum length of 8 characters */
    private String password;

}
