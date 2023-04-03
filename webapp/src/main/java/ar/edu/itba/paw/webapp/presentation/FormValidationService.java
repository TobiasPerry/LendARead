package ar.edu.itba.paw.webapp.presentation;

public interface FormValidationService extends Iterable<String> {
    void addInvalidFormElement(FormElement formElement);

    boolean isValid();
}
