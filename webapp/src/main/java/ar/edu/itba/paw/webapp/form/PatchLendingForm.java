package ar.edu.itba.paw.webapp.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PatchLendingForm {

    @NotNull
    @Pattern(regexp = "DELIVERED|REJECTED|FINISHED|CANCEL", message = "{lending.state.invalid}")
    private String state;
}
