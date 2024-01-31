package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.interfaces.Isbn;
import ar.edu.itba.paw.webapp.form.annotations.interfaces.Language;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddAssetForm {

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @NotNull
    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    @Size(min = 1, max = 100)
    private String author;

    @NotNull
    @Isbn
    @Size(min = 1, max = 100)
    private String isbn;

    @NotNull
    @Size(min = 1, max = 3)
    @Language
    private String language;

}
