package ar.edu.itba.paw.webapp.presentation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FormValidationServiceImpl implements FormValidationService {
    private final List<FormElement> invalidElements;

    FormValidationServiceImpl(){
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

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private final Iterator<FormElement> formElementIterator = invalidElements.iterator();

            @Override
            public boolean hasNext() {
                return formElementIterator.hasNext();
            }

            @Override
            public String next() {
                FormElement formElement = formElementIterator.next();
                return formElement.getInputName() + ": " + formElement.getInvalidInputFormatMessage();
            }
        };
    }
}
