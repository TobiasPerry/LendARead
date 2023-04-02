package ar.edu.itba.paw.webapp.presentation;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormElementsAddAsset implements FormElements {

    private final FormElement[] bookInfoElements = new FormElementImpl[] {
            new FormElementNonNullString("Título", "title"),
            new FormElementNonNullString("Autor",  "author"),
            new FormElementNonNullString("Idioma", "language"),
            new FormElementNonNullString("ISBN", "isbn"),
            new FormElementImpl("Estado", "select", "physicalCondition", "", new String[] {"asnew", "fine", "verygood", "good", "fair", "poor", "exlibrary", "bookclub", "bindingcopy"})
    };;
    private final FormElement[] locationInfoElements  = new FormElementImpl[] {
            new FormElementZipcode("Codigo postal",  "zipcode"),
            new FormElementNonNullString("Localidad", "locality"),
            new FormElementNonNullString("Provincia", "province"),
            new FormElementNonNullString("Pais",  "country")
    };;
    private final FormElement[] contactInfoElements  = new FormElementImpl[] {
            new FormElementEmail("Mail",  "email"),
            new FormElementNonNullString("Nombre",  "name"),
            new FormElementNonNullString("Mensaje para Retirarlo", "message")
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

}
