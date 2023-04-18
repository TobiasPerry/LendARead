package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Email;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.NotEmpty;

import javax.validation.constraints.Size;

public class BorrowAssetForm {
     @Email
     @Size(min = 2, max = 100)
     private String email;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
