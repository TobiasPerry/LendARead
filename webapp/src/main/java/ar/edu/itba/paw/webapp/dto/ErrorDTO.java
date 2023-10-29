package ar.edu.itba.paw.webapp.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

    private String attribute;
    private String message;

    public static ErrorDTO fromMessage(String attribute, String message) {
        ErrorDTO errorValidationDto = new ErrorDTO();
        errorValidationDto.attribute = attribute;
        errorValidationDto.message = message;
        return errorValidationDto;
    }


}