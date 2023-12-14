package ar.edu.itba.paw.webapp.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

    private String message;

    public static ErrorDTO fromError( String message) {
        ErrorDTO errorValidationDto = new ErrorDTO();
        errorValidationDto.message = message;
        return errorValidationDto;
    }


}