package ar.edu.itba.paw.webapp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorsDTO {

    private  List<ErrorDTO> errors;
    private  int errorsCount;


    public static ErrorsDTO fromValidationError(ConstraintViolationException e) {
        ErrorsDTO errorsDTO = new ErrorsDTO();
        errorsDTO.errors = new ArrayList<>();
        e.getConstraintViolations().forEach(violation -> errorsDTO.errors.add(ErrorDTO.fromError(violation.getMessage(), getViolationPropertyName(violation))));
        errorsDTO.errorsCount = e.getConstraintViolations().size();
        return errorsDTO;
    }
    private static String getViolationPropertyName(ConstraintViolation<?> violation) {
        final String propertyPath = violation.getPropertyPath().toString();
        return propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
    }
}
