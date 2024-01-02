package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class AssetInstanceForm {

    @NotNull
    @Pattern(regexp = "ASNEW|FINE|VERYGOOD|GOOD|FAIR|POOR|EXLIBRARY|BOOKCLUB|BINDINGCOPY")
    @FormDataParam("physicalCondition")
    private String physicalCondition;

    @NotNull
    @Min(value = 1)
    @FormDataParam("assetId")
    private Long assetId;

    @NotNull
    @Min(value = 1)
    @FormDataParam("maxDays")
    private Integer maxDays;

    @NotNull
    @Size(min = 0, max = 1000)
    @FormDataParam("description")
    private String description;

    @NotNull
    @Min(value = 0)
    @FormDataParam("locationId")
    private Integer locationId;

    @NotNull
    @FormDataParam("isReservable")
    private Boolean isReservable;

    @NotNull
    @Pattern(regexp = "PRIVATE|PUBLIC")
    @FormDataParam("state")
    private String state;

    @Image
    @FormDataParam("image")
    private FormDataBodyPart image;

    @FormDataParam("image")
    private byte[] imageBytes;

}
