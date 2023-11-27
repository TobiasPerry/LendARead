package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PatchLendingForm {

    @FormDataParam("state")
    @Pattern(regexp = "ACCEPTED|REJECTED|PENDING")
    private String state;
}
