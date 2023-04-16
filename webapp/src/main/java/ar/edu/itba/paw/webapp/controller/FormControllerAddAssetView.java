package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.LibraryAPIService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
final public class FormControllerAddAssetView {

    LibraryAPIService libraryAPIService;

    @Autowired
    public FormControllerAddAssetView(LibraryAPIService libraryAPIService) {
        this.libraryAPIService = libraryAPIService;
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

        Book book;
        try {
            book = this.libraryAPIService.getBookByISBN(request.getIsbn());
        } catch (IOException exc) {
            book = BookFactory.createBook(request.getIsbn(),request.getAuthor(),request.getTitle(),request.getLanguage());
        }

        PhysicalCondition physicalCondition = PhysicalCondition.fromString(request.getPhysicalCondition());

        return AssetInstanceFactory.createAssetInstance(-1, book, physicalCondition, user, location,-1, AssetState.PUBLIC);
    }
}
