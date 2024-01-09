package ar.edu.itba.paw.webapp.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

    private String message;

    private String field;

    public static ErrorDTO fromError( String message, String field) {
        ErrorDTO errorValidationDto = new ErrorDTO();
        errorValidationDto.message = message;
        errorValidationDto.field = field;
        return errorValidationDto;
    }


}