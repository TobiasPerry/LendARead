package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;

@Getter @Setter
public class UserAssetFilterSortForm {

    @Min(0)
    private int currentPage;

    private String table;

    private String attribute;

    private String direction;

    private String filterValue;

    private String filterAtribuite;
}
