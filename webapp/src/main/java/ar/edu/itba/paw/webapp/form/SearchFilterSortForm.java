package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class SearchFilterSortForm {

    @Size(min = 1, max = 100)
    private String search;

    private List<String> physicalConditions;

    private List<String> languages;

    @Pattern(regexp = "AUTHOR_NAME|TITLE_NAME|RECENT|DEFAULT")
    private String sort;

    @Pattern(regexp = "ASCENDING|DESCENDING|DEFAULT")
    private String sortDirection;

    @Min(0)
    private int currentPage;

    @Min(1)
    @Max(5)
    private int minRating;

    @Min(1)
    @Max(5)
    private int maxRating;

    public SearchFilterSortForm() {
        this.currentPage = 1;
        this.minRating = 1;
        this.maxRating = 5;
    }

}

