package ar.edu.itba.paw.webapp.presentation;

public class FormElement {
    private String label;
    private String inputType;
    private String inputName;
    private String inputValue;
    private String[] selectOptions;

    public FormElement(String label, String inputType, String inputName, String inputValue, String[] selectOptions) {
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

    // Constructor, getters, and setters
}
