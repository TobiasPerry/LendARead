package ar.edu.itba.paw.webapp.presentation;

import java.util.regex.Pattern;

public class FormElementEmail extends FormElementImpl{

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    public FormElementEmail(String label, String inputName) {
        super(label, "text", inputName, "");
    }

    @Override
    public String getInputType() {
        return "Ingrese un email valido";
    }

    @Override
    public boolean isValidInput(String input) {
        return EMAIL_PATTERN.matcher(input).matches();
    }

    @Override
    public String getInvalidInputFormatMessage() {
        return "ingrese una direccion email";
    }

}
