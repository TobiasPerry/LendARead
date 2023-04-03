package ar.edu.itba.paw.webapp.presentation;

public class FormElementNonNullString extends FormElementImpl{

    public FormElementNonNullString(String label, String inputName) {
        super(label, "text", inputName, "");
    }


    @Override
    public boolean isValidInput(String input) {
        return input != null;
    }

    @Override
    public String getInvalidInputFormatMessage() {
        return "es un valor obligatorio";
    }
}
