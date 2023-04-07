package ar.edu.itba.paw.webapp.presentation;

import models.assetExistanceContext.factories.AssetInstanceFactory;
import models.assetExistanceContext.factories.BookFactory;
import models.assetExistanceContext.implementations.AssetInstanceImpl;
import models.assetExistanceContext.implementations.BookImpl;
import models.assetExistanceContext.implementations.PhysicalCondition;
import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetExistanceContext.interfaces.Book;
import models.userContext.factories.LocationFactory;
import models.userContext.factories.UserFactory;
import models.userContext.interfaces.Location;
import models.userContext.interfaces.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
final public class FormServiceAddAssetView implements FormService {

    private final FormElement[] bookInfoElements = new FormElementImpl[] {
            new FormElementNonEmptyString("Título", "title"),
            new FormElementNonEmptyString("Autor",  "author"),
            new FormElementNonEmptyString("Idioma", "language"),
            new FormElementISBN("ISBN", "isbn"),
    };;

    private final FormElementDropdown conditionsDropDown = new FormElementDropdown("Estado", "physicalCondition",  new String[] {"ASNEW", "FINE", "VERYGOOD", "GOOD", "FAIR", "POOR", "EXLIBRARY", "BOOKCLUB", "BINDINGCOPY"});
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

    public AssetInstance createAssetInstance(HttpServletRequest request) {

        User user = UserFactory.createUser(
                request.getParameter("email"),
                request.getParameter("name"),
                request.getParameter("message")
        );

        Location location = LocationFactory.createLocation(
                request.getParameter("zipcode"),
                request.getParameter("locality"),
                request.getParameter("province"),
                request.getParameter("country")
        );

        Book book = BookFactory.createBook(
                request.getParameter("isbn"),
                request.getParameter("author"),
                request.getParameter("title"),
                request.getParameter("language"),
                null);

        PhysicalCondition physicalCondition = PhysicalCondition.fromString(request.getParameter("physicalCondition"));

        return AssetInstanceFactory.createAssetInstance(book, physicalCondition, user, location);
    }
}
