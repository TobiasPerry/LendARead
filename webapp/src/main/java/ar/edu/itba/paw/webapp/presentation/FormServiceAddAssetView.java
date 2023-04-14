package ar.edu.itba.paw.webapp.presentation;

import ar.edu.itba.paw.models.assetExistanceContext.factories.AssetInstanceFactory;
import ar.edu.itba.paw.models.assetExistanceContext.factories.BookFactory;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.factories.LocationFactory;
import ar.edu.itba.paw.models.userContext.factories.UserFactory;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
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

    public AssetInstance createAssetInstance(AddAssetForm request) {

        User user = UserFactory.createUser(-1,
                request.getEmail(),
                request.getName(),
                request.getMessage()
        );

        Location location = LocationFactory.createLocation(
                -1,
                request.getZipcode(),
                request.getLocality(),
                request.getProvince(),
                request.getCountry()
        );

        Book book = BookFactory.createBook(
                request.getIsbn(),
                request.getAuthor(),
                request.getTitle(),
                request.getLanguage(),
                null);

        PhysicalCondition physicalCondition = PhysicalCondition.fromString(request.getPhysicalCondition());

        return AssetInstanceFactory.createAssetInstance(-1, book, physicalCondition, user, location,-1, AssetState.PUBLIC);
    }
}
