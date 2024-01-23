package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class AssetInstanceForm {

    @NotNull
    @Pattern(regexp = "ASNEW|FINE|VERYGOOD|GOOD|FAIR|POOR|EXLIBRARY|BOOKCLUB|BINDINGCOPY", message = "{Pattern.assetInstanceForm.physicalCondition}")
    private String physicalCondition;

    @NotNull
    @Min(value = 1)
    private Long assetId;

    @NotNull
    @Min(value = 1)
    private Integer maxDays;

    @NotNull
    @Size(min = 0, max = 1000)
    private String description;

    @NotNull
    @Min(value = 0)
    private Integer locationId;

    @NotNull
    private Boolean isReservable;

    @NotNull
    @Pattern(regexp = "PRIVATE|PUBLIC", message = "{Pattern.assetInstanceForm.visibility}")
    private String status;

    @NotNull
    private Integer imageId;
}
