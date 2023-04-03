package ar.edu.itba.paw.webapp.presentation;

import java.util.Iterator;

public interface FormValidation extends Iterable<String> {
    void addInvalidFormElement(FormElement formElement);

    boolean isValid();
}
