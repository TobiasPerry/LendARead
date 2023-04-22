package ar.edu.itba.paw.webapp.formFactories;

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

final public class FormFactoryAddAssetView {

    private final static String DEFAULT_STRING_VALUE = "";

    private final static int ID_DEFAULT_VALUE = -1;

    public static AssetInstance createAssetInstance(AddAssetForm request,String email) {

        User user = UserFactory.createUser(ID_DEFAULT_VALUE,
                email,
                DEFAULT_STRING_VALUE,
                DEFAULT_STRING_VALUE,
                DEFAULT_STRING_VALUE
        );

        Location location = LocationFactory.createLocation(
                ID_DEFAULT_VALUE,
                request.getZipcode(),
                request.getLocality(),
                request.getProvince(),
                request.getCountry()
        );


        Book book = BookFactory.createBook(request.getIsbn(),request.getAuthor(),request.getTitle(),request.getLanguage());


        PhysicalCondition physicalCondition = PhysicalCondition.fromString(request.getPhysicalCondition());

        return AssetInstanceFactory.createAssetInstance(ID_DEFAULT_VALUE, book, physicalCondition, user, location,ID_DEFAULT_VALUE, AssetState.PUBLIC);
    }
}
