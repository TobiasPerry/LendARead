package ar.edu.itba.paw.webapp.miscellaneous;

import ar.edu.itba.paw.models.assetExistanceContext.factories.BookFactory;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.miscellaneous.ImageImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
import org.springframework.web.multipart.MultipartFile;

final public class FormFactoryAddAssetView {


    public static byte[] getByteArray(MultipartFile file) {
            if (!file.isEmpty()) {
                try {
                    return file.getBytes();
                } catch (Exception e) {
                    //
                }
            }
            return null;
    }

    public static AssetInstanceImpl createAssetInstance(AddAssetForm request, UserImpl user, LocationImpl location) {


        BookImpl book = BookFactory.createBook(request.getIsbn(),request.getAuthor(),request.getTitle(),request.getLanguage());


        PhysicalCondition physicalCondition = PhysicalCondition.fromString(request.getPhysicalCondition());

        return new AssetInstanceImpl(book, physicalCondition, user, location, new ImageImpl(), AssetState.PUBLIC,request.getMaxDays(),request.getDescription(), request.getIsReservable());
    }
}
