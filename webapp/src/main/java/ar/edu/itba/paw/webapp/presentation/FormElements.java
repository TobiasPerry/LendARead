package ar.edu.itba.paw.webapp.presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormElements {

    private final FormElement[] bookInfoElements;
    private final FormElement[] locationInfoElements;
    private final FormElement[] contactInfoElements;

    public FormElements() {
        // Book Information
        String[] physicalConditions = new String[] {"asnew", "fine", "verygood", "good", "fair", "poor", "exlibrary", "bookclub", "bindingcopy"};

        bookInfoElements = new FormElement[] {
                new FormElement("Título", "text", "title", "", null),
                new FormElement("Autor", "text", "author", "", null),
                new FormElement("Idioma", "text", "language", "", null),
                new FormElement("ISBN", "text", "isbn", "", null),
                new FormElement("Estado", "select", "physicalCondition", "", physicalConditions)
        };

        // Location Information
        locationInfoElements = new FormElement[] {
                new FormElement("Codigo postal", "text", "zipcode", "", null),
                new FormElement("Localidad", "text", "locality", "", null),
                new FormElement("Provincia", "text", "province", "", null),
                new FormElement("Pais", "text", "country", "", null)
        };

        // Contact Information
        contactInfoElements = new FormElement[] {
                new FormElement("Mail", "text", "email", "", null),
                new FormElement("Nombre", "text", "name", "", null),
                new FormElement("Mensaje para Retirarlo", "text", "message", "", null)
        };
    }

    public FormElement[] getBookInfoElements() {
        return bookInfoElements;
    }

    public FormElement[] getLocationInfoElements() {
        return locationInfoElements;
    }

    public FormElement[] getContactInfoElements() {
        return contactInfoElements;
    }
}
