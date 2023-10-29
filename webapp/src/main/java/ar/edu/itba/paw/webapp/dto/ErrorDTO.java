package ar.edu.itba.paw.webapp.dto;

public class ErrorDTO {

    private String attribute;
    private String message;

    public static ErrorDTO fromValidationError(String attribute, String message) {
        ErrorDTO errorValidationDto = new ErrorDTO();
        errorValidationDto.attribute = attribute;
        errorValidationDto.message = message;
        return errorValidationDto;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}