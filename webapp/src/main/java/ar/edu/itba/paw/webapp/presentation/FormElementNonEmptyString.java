package ar.edu.itba.paw.webapp.presentation;

public class FormElementNonEmptyString extends FormElementImpl{

    public FormElementNonEmptyString(String label, String inputName) {
        super(label, "text", inputName, "");
    }


    @Override
    public boolean isValidInput(String input) {
        return !input.isEmpty();
    }

    @Override
    public String getInvalidInputFormatMessage() {
        return "es un valor obligatorio";
    }
}
