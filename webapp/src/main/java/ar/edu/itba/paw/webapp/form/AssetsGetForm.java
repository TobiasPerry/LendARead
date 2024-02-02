package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

@Getter @Setter
public class AssetsGetForm {
    @QueryParam(value = "page")  @Min(1)  @DefaultValue("1")  int page;
    @QueryParam(value = "itemsPerPage")  @Min(1) @DefaultValue("10")  int itemsPerPage;
    @QueryParam(value = "isbn")   String isbn;
    @QueryParam(value = "author")   String author;
    @QueryParam(value = "title")   String title;
    @QueryParam(value = "language")   String language;
}
