package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LocationForm {

    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    @FormDataParam("zipcode")
    private String zipcode;

    @NotNull
    @Size(min = 1, max = 100)
    @FormDataParam("locality")
    private String locality;

    @NotNull
    @Size(min = 4, max = 100)
    @FormDataParam("province")
    private String province;

    @NotNull
    @Size(min = 4, max = 100)
    @FormDataParam("country")
    private String country;

    @NotNull
    @Size(min = 1, max = 100)
    @FormDataParam("name")
    private String name;
}
