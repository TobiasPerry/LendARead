package ar.edu.itba.paw.webapp.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter
public class SearchFilterSortForm {

    @Size(min = 1)
    private String search;

    private List<String> physicalConditions;

    private List<String> languages;

    @Min(0)
    private int currentPage;

    public SearchFilterSortForm() {
        this.currentPage = 1;
    }

}

