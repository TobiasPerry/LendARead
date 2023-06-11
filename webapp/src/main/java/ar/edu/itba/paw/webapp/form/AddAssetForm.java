package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Isbn;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddAssetForm {

    @Isbn
    private String isbn;

    @Size(min = 1, max = 100)
    private String language;

    @Size(min = 1, max = 100)
    private String author;

    @Size(min = 1, max = 1000)
    private String title;

    @Size(min = 1, max = 100)
    private String physicalCondition;

    @Min(value = 0)
    private int id;
    @Size(min = 0, max = 300)
    private String description;

    @Min(value = 1)
    private int maxDays;

    private String languageSelect;
}
