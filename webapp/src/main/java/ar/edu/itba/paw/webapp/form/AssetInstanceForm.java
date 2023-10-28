package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter @Setter
public class AssetInstanceForm {
    @Size(min = 1, max = 100)
    private String physicalCondition;

    private int id;

    @Min(value = 1)
    private Integer maxDays;

    @Size(min = 0, max = 1000)
    private String description;

    private Integer locationId;

    @Image
    @FormDataParam("image")
    private FormDataBodyPart image;

    @FormDataParam("image")
    private byte[] imageBytes;

}
