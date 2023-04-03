package ar.edu.itba.paw.webapp.presentation;

import java.util.Arrays;

public class FormElementDropdown extends FormElementImpl{

    String[] selectOptions;

    public FormElementDropdown(String label, String inputName, String[] selectOptions) {
        super(label, "select", inputName, "");
        this.selectOptions = selectOptions;
    }

    public String[] getSelectOptions() {
        return selectOptions;
    }

    @Override
    public boolean isValidInput(String input) {
       for (String option : selectOptions)
           if(input.equals(option))
               return true;

       return false;
    }

    @Override
    public String getInvalidInputFormatMessage() {
        return "ingrese una opcion valida";
    }
}
