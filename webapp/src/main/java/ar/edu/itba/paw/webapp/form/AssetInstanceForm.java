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
    @FormDataParam("physicalCondition")
    private String physicalCondition;


    @Min(value = 1)
    @FormDataParam("maxDays")
    private Integer maxDays;

    @Size(min = 0, max = 1000)
    @FormDataParam("description")
    private String description;

    @Min(value = 0)
    @FormDataParam("locationId")
    private Integer locationId;

    @Min(value = 1)
    @FormDataParam("photoId")
    private Integer photoId;

    @FormDataParam("isReservable")
    private Boolean isReservable;

    @Image
    @FormDataParam("image")
    private FormDataBodyPart image;

    @FormDataParam("image")
    private byte[] imageBytes;




}
