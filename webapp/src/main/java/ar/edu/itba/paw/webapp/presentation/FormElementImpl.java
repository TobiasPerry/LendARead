package ar.edu.itba.paw.webapp.presentation;

import java.util.function.Predicate;

public class FormElementImpl implements FormElement {
    private final String label;
    private final String inputType;
    private final String inputName;
    private final String inputValue;
    private final String[] selectOptions;

    public FormElementImpl(String label, String inputType, String inputName, String inputValue, String[] selectOptions) {
        this.label = label;
        this.inputType = inputType;
        this.inputName = inputName;
        this.inputValue = inputValue;
        this.selectOptions = selectOptions;
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

    public String[] getSelectOptions() {
        return selectOptions;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean isValidInput(String input) {
        return true;
    }
}
