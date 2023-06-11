package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter @Setter
public class AssetInstanceForm {
    @Size(min = 1, max = 100)
    private String physicalCondition;

    private int id;

    @Min(value = 1)
    private int maxDays;

    @Size(min = 20, max = 300)
    private String description;

    @Image
    private MultipartFile image;
}
