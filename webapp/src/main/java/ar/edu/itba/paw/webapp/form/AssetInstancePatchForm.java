package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AssetInstancePatchForm {

    @Size(min = 1, max = 100)
    @Pattern(regexp = "ASNEW|FINE|VERYGOOD|GOOD|FAIR|POOR|EXLIBRARY|BOOKCLUB|BINDINGCOPY", message = "{Pattern.assetInstanceForm.physicalCondition}")
    private String physicalCondition;

    @Min(value = 1)
    private Integer maxDays;

    @Size(min = 0, max = 1000)
    private String description;


    @Min(value = 0)
    private Integer locationId;

    private Boolean isReservable;

    @Pattern(regexp = "PRIVATE|PUBLIC", message = "{Pattern.assetInstanceForm.visibility}")
    private String status;

    @Min(value = 1)
    private Integer imageId;

}