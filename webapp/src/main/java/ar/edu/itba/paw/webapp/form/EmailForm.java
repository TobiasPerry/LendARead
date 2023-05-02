package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.EmailExistence;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailForm {

    @Email @EmailExistence
    private String email;
}
