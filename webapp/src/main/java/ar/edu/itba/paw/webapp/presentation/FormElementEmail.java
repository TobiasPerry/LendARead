package ar.edu.itba.paw.webapp.presentation;

import java.util.regex.Pattern;

public class FormElementEmail extends FormElementImpl{
    public FormElementEmail(String label, String inputName) {
        super(label, "text", inputName, "", null);
    }

    @Override
    public boolean isValidInput(String input) {
        return input != null && Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", input);
    }
}
