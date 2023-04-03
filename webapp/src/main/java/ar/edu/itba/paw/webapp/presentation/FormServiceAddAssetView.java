package ar.edu.itba.paw.webapp.presentation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormServiceAddAssetView implements FormService {

    private final FormElement[] bookInfoElements = new FormElementImpl[] {
            new FormElementNonEmptyString("Título", "title"),
            new FormElementNonEmptyString("Autor",  "author"),
            new FormElementNonEmptyString("Idioma", "language"),
            new FormElementISBN("ISBN", "isbn"),
    };;

    private final FormElementDropdown conditionsDropDown = new FormElementDropdown("Estado", "physicalCondition",  new String[] {"asnew", "fine", "verygood", "good", "fair", "poor", "exlibrary", "bookclub", "bindingcopy"});
    private final FormElement[] locationInfoElements  = new FormElementImpl[] {
            new FormElementZipcode("Codigo postal",  "zipcode"),
            new FormElementNonEmptyString("Localidad", "locality"),
            new FormElementNonEmptyString("Provincia", "province"),
            new FormElementNonEmptyString("Pais",  "country")
    };;
    private final FormElement[] contactInfoElements  = new FormElementImpl[] {
            new FormElementEmail("Mail",  "email"),
            new FormElementNonEmptyString("Nombre",  "name"),
            new FormElementNonEmptyString("Mensaje para Retirarlo", "message")
    };;

    public FormElement[] getBookInfoElements() { return bookInfoElements; }

    public FormElement[] getLocationInfoElements() {
        return locationInfoElements;
    }

    public FormElement[] getContactInfoElements() {
        return contactInfoElements;
    }

    public List<List<FormElement>> getAllFormElementLists() {
        List<FormElement[]> formElementArrays = Arrays.asList(
                getBookInfoElements(),
                getLocationInfoElements(),
                getContactInfoElements()
        );

        return formElementArrays.stream()
                .map(Arrays::asList)
                .collect(Collectors.toList());
    }

    public FormElementDropdown getConditionsDropDown() {
        return this.conditionsDropDown;
    }
}
