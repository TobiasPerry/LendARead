package ar.edu.itba.paw.webapp.presentation;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class FormElementsAddAsset implements FormElements {

    private final FormElement[] bookInfoElements = new FormElement[] {
            new FormElement("Título", "text", "title", "", null),
            new FormElement("Autor", "text", "author", "", null),
            new FormElement("Idioma", "text", "language", "", null),
            new FormElement("ISBN", "text", "isbn", "", null),
            new FormElement("Estado", "select", "physicalCondition", "", new String[] {"asnew", "fine", "verygood", "good", "fair", "poor", "exlibrary", "bookclub", "bindingcopy"})
    };;
    private final FormElement[] locationInfoElements  = new FormElement[] {
            new FormElement("Codigo postal", "text", "zipcode", "", null),
            new FormElement("Localidad", "text", "locality", "", null),
            new FormElement("Provincia", "text", "province", "", null),
            new FormElement("Pais", "text", "country", "", null)
    };;
    private final FormElement[] contactInfoElements  = new FormElement[] {
            new FormElement("Mail", "text", "email", "", null),
            new FormElement("Nombre", "text", "name", "", null),
            new FormElement("Mensaje para Retirarlo", "text", "message", "", null)
    };;

    public FormElement[] getBookInfoElements() { return bookInfoElements; }

    public FormElement[] getLocationInfoElements() {
        return locationInfoElements;
    }

    public FormElement[] getContactInfoElements() {
        return contactInfoElements;
    }

    @Override
    public FormValidation validateRequest(HttpServletRequest request) {
        List<FormElement[]> allFormElementLists = Arrays.asList(
                getBookInfoElements(),
                getLocationInfoElements(),
                getContactInfoElements()
        );

        FormValidation response = new FormValidationImpl();

        for (FormElement[] formElementList : allFormElementLists) {
            for (FormElement formElement : formElementList) {
                String inputName = formElement.getInputName();
                String requestValue = request.getParameter(inputName);
                if (requestValue == null || requestValue.isEmpty()) {
                    response.addInvalidFormElement(formElement);
                }
            }
        }

        return response;
    }
}
