package ar.edu.itba.paw.webapp.presentation;

public class FormElementZipcode extends FormElementImpl{

    public FormElementZipcode(String label,String inputName) {
        super(label, "text", inputName, "", null);
    }

    @Override
    public boolean isValidInput(String input) {
        return false;
    }
}
