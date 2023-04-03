package ar.edu.itba.paw.webapp.presentation;

import java.util.function.Predicate;

public abstract class FormElementImpl implements FormElement {
    private final String label;
    private final String inputType;
    private final String inputName;
    private final String inputValue;

    private String status = "white";

    public FormElementImpl(String label, String inputType, String inputName, String inputValue) {
        this.label = label;
        this.inputType = inputType;
        this.inputName = inputName;
        this.inputValue = inputValue;
    }

    @Override
    public void setInvalid() {
        this.status = "red";
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    public String getInputType() {
        return inputType;
    }

    public String getInputName() {
        return inputName;
    }

    public String getInputValue() {
        return inputValue;
    }

    public String getLabel() {
        return label;
    }

}
