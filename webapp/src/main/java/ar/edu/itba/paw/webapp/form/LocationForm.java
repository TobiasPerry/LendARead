package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LocationForm {

    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{Pattern.locationForm.zipcode}")
    private String zipcode;

    @NotNull
    @Size(min = 1, max = 100)
    private String locality;

    @NotNull
    @Size(min = 4, max = 100)
    private String province;

    @NotNull
    @Size(min = 4, max = 100)
    private String country;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;
}
