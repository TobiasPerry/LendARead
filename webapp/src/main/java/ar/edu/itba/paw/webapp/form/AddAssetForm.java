package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Isbn;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddAssetForm {

    @Isbn
    @FormDataParam("isbn")
    private String isbn;

    @Size(min = 1, max = 100)
    @FormDataParam("language")
    private String language;

    @Size(min = 1, max = 100)
    @FormDataParam("author")
    private String author;

    @Size(min = 1, max = 1000)
    @FormDataParam("title")
    private String title;

    @Size(min = 1, max = 100)
    @FormDataParam("physicalCondition")
    private String physicalCondition;

    @Min(value = 0)
    @FormDataParam("location")
    private Integer locationId;

    @Size(min = 0, max = 1000)
    @FormDataParam("description")
    private String description;

    @Min(value = 1)
    @FormDataParam("maxDays")
    private Integer maxDays;

    @FormDataParam("isReservable")
    private Boolean isReservable;

    @FormDataParam("languageSelect")
    private String languageSelect;
    @Image
    @FormDataParam("image")
    private  FormDataBodyPart image;

    @FormDataParam("image")
    private byte[] imageBytes;

}
