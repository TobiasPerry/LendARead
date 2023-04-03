package ar.edu.itba.paw.webapp.presentation;

public interface FormElement {

    String getInputName();

     boolean isValidInput(String input);
    String getInvalidInputFormatMessage();

    String getLabel();

    String getInputType();

    String getStatus();

    void setInvalid();
}
