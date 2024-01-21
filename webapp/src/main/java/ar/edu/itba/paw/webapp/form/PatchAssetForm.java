package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Isbn;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Language;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class PatchAssetForm {

    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 100)
    private String description;

    @Size(min = 1, max = 100)
    private String author;

    @Isbn
    @Size(min = 1, max = 100)
    private String isbn;

    @Size(min = 1, max = 3)
    @Language
    private String language;

}
