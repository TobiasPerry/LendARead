package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EditLocationForm {

        @Size(min = 1, max = 100)
        @Pattern(regexp = "^[a-zA-Z0-9]+$")
        @FormDataParam("zipcode")
        private String zipcode;

        @Size(min = 1, max = 100)
        @FormDataParam("locality")
        private String locality;

        @Size(min = 4, max = 100)
        @FormDataParam("province")
        private String province;

        @Size(min = 4, max = 100)
        @FormDataParam("country")
        private String country;

        @Size(min = 1, max = 100)

        @FormDataParam("name")
        private String name;
}
