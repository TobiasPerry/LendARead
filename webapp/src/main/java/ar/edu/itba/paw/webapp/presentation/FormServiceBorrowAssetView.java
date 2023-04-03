package ar.edu.itba.paw.webapp.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FormServiceBorrowAssetView implements FormService{

    private final FormElement[] locationInfoElements  = new FormElementImpl[] {
            new FormElementZipcode("Codigo postal",  "zipcode"),
            new FormElementNonEmptyString("Localidad", "locality"),
            new FormElementNonEmptyString("Provincia", "province"),
            new FormElementNonEmptyString("Pais",  "country")
    };

    private final FormElement[] contactInfoElements  = new FormElementImpl[] {
            new FormElementEmail("Mail",  "email"),
            new FormElementNonEmptyString("Nombre",  "name"),
            new FormElementNonEmptyString("Mensaje para Retirarlo", "message")
    };

    @Override
    public List<List<FormElement>> getAllFormElementLists() {
        List<List<FormElement>> allFormElementLists = new ArrayList<>();

        allFormElementLists.add(Arrays.asList(locationInfoElements));
        allFormElementLists.add(Arrays.asList(contactInfoElements));

        return allFormElementLists;
    }

    public FormElement[] getLocationInfoElements() {
        return locationInfoElements;
    }

    public FormElement[] getContactInfoElements() {
        return contactInfoElements;
    }

}
