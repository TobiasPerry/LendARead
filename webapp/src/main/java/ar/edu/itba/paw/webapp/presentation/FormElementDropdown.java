package ar.edu.itba.paw.webapp.presentation;

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
    public String getInvalidInputFormatMessage() {
        return "ingrese una opcion valida";
    }
}
