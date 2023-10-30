package ar.edu.itba.paw.webapp.form;

import com.sun.istack.internal.Nullable;
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
        @Nullable
        @FormDataParam("zipcode")
        private String zipcode;

        @Size(min = 1, max = 100)
        @Nullable
        @FormDataParam("locality")
        private String locality;

        @Size(min = 4, max = 100)
        @Nullable
        @FormDataParam("province")
        private String province;

        @Size(min = 4, max = 100)
        @Nullable
        @FormDataParam("country")
        private String country;

        @Size(min = 1, max = 100)
        @Nullable
        @FormDataParam("name")
        private String name;
}
