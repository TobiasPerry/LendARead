package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class AssetInstanceForm {
    @Size(min = 1, max = 100)
    private String physicalCondition;

    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String zipcode;

    @Size(min = 1, max = 100)
    private String locality;

    @Size(min = 1, max = 100)
    private String province;

    @Size(min = 1, max = 100)
    private String country;

    @Min(value = 1)
    private int maxDays;

    @Image
    private MultipartFile image;
}
