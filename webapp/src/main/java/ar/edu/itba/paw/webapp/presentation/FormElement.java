package ar.edu.itba.paw.webapp.presentation;

public interface FormElement {

    boolean isValidInput(String input);

    String getInputName();
}
