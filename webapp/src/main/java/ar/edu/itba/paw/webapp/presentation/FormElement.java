package ar.edu.itba.paw.webapp.presentation;

public interface FormElement {

    String getInputName();

    default boolean isValidInput(String input) {
        return true;
    }
    default String getValidInputFormat() {
        return "";
    }
}
