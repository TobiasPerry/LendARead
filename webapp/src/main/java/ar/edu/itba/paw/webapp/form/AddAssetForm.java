package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Isbn;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class AddAssetForm {

    @Isbn
    private String isbn;
    @Size(min = 3,max = 100)
    private String language;
    @Size(min = 3,max = 100)
    private String author;

    @Size(min = 3,max = 100)
    private String title;

    @Size(min = 3,max = 100)
    private String physicalCondition;

    @Pattern(regexp = "^\\d{4}$")
    private String zipcode;

    @Size(min = 3,max = 100)
    private String locality;

    @Size(min = 3,max = 100)
    private String province;

    @Size(min = 3,max = 100)
    private String country;

    @Min(value = 0)
    @Max(value = 10)
    private int maxWeeks;

    @Min(value = 0)
    @Max(value = 10)
    private int maxDays;
}
