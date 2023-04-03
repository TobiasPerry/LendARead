package ar.edu.itba.paw.webapp.presentation;

public interface FormElement {

    String getInputName();

    default boolean isValidInput(String input) {
        return true;
    }
    String getInvalidInputFormatMessage();

    String getLabel();

    String getInputType();
}
