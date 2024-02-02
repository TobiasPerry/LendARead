package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;

@Getter @Setter
public class AssetInstanceGetForm {
    @QueryParam("search")  @Size(min = 1, max = 100) String search;
    @QueryParam("physicalConditions")
    List<String> physicalConditions;
    @QueryParam("languages")  List<String> languages;
    @QueryParam("sort") @Pattern(regexp = "AUTHOR_NAME|TITLE|RECENT|LANGUAGE|STATE|PHYSICAL_CONDITION",message = "{pattern.sort.AssetInstance}") String sort;
    @QueryParam("sortDirection") @Pattern(regexp = "ASCENDING|DESCENDING",message = "{pattern.SortDirection}") String sortDirection;
    @QueryParam("page")  @DefaultValue("1")  @Min(1) int page;
    @QueryParam("status") @DefaultValue("PUBLIC") @Pattern(regexp = "PUBLIC|PRIVATE|ALL",message = "{pattern.status}") String status;
    @QueryParam("minRating")  @DefaultValue("1")@Min(1) @Max(5) int minRating;
    @QueryParam("maxRating")  @DefaultValue("5") @Min(1) @Max(5)int maxRating;
    @QueryParam("itemsPerPage") @DefaultValue("10") int itemsPerPage;
    @QueryParam("userId")  @DefaultValue("-1") int userId;
}
