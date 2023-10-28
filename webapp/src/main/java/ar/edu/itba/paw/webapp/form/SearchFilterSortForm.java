package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.QueryParam;
import java.util.List;

@Getter
@Setter
public class SearchFilterSortForm {
    @QueryParam("search")
    @Size(min = 1, max = 100)
    private String search;

    @QueryParam("physicalConditions")
    private List<String> physicalConditions;

    @QueryParam("languages")
    private List<String> languages;

    @QueryParam("sort")
    @Pattern(regexp = "AUTHOR_NAME|TITLE_NAME|RECENT|DEFAULT")
    private String sort;
    @QueryParam("sortDirection")
    @Pattern(regexp = "ASCENDING|DESCENDING|DEFAULT")
    private String sortDirection;

    @QueryParam("page")
    @Min(0)
    private int currentPage = 0;

    @QueryParam("minRating")
    @Min(1)
    @Max(5)
    private int minRating;

    @QueryParam("maxRating")
    @Min(1)
    @Max(5)
    private int maxRating;

    @QueryParam("itemsPerPage")
    @Min(1)
    private int itemsPerPage = 1;

    public SearchFilterSortForm() {
        this.currentPage = 1;
        this.minRating = 1;
        this.maxRating = 5;
    }

}

