package ar.edu.itba.paw.webapp.presentation;

import java.util.ArrayList;
import java.util.List;

public class FormValidationImpl implements FormValidation {
    private final List<FormElement> invalidElements;

    FormValidationImpl(){
        invalidElements = new ArrayList<>();
    }

    @Override
    public void addInvalidFormElement(FormElement formElement) {
        invalidElements.add(formElement);
    }

    @Override
    public boolean isValid() {
        return invalidElements.isEmpty();
    }
}
