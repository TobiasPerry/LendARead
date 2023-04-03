package ar.edu.itba.paw.webapp.presentation;

import java.util.regex.Pattern;

public class FormElementZipcode extends FormElementImpl{
    private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("^\\d{4}$");

    public FormElementZipcode(String label,String inputName) {
        super(label, "text", inputName, "");
    }

    @Override
    public boolean isValidInput(String input) {
        return ZIP_CODE_PATTERN.matcher(input).matches();
    }

    @Override
    public String getInvalidInputFormatMessage() {
        return "ingrese un codigo postal de 4 digitos";
    }
}
