package ar.edu.itba.paw.webapp.presentation;

public interface FormValidation {
    void addInvalidFormElement(FormElement formElement);

    boolean isValid();
}
