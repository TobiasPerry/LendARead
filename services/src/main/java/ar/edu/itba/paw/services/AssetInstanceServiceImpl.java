package ar.edu.itba.paw.services;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AssetInstanceServiceImpl implements AssetInstanceService {

    @Autowired
    AssetDao assetDao;

    @Override
    public HashMap<String, String> getAssetInstanceDisplay(String isbn) {
        // Mock. Debe conectarse con la capa de persistencia.
        // TODO. Una vez la capa de persistencia este implementada, devolverla
      //  AssetInstance assetInstance = new AssetInstanceImpl();
        // HashMap<String, String> info = assetInstance.display(); TODO

        Optional<Book> opt = this.assetDao.getBook(isbn);
        if (! opt.isPresent()) {
            return null;
        }

        Book book = opt.get();

        HashMap<String, String> info = new HashMap<>();
//        info.put("id", id);
        info.put("name", book.getName());
        info.put("type", book.type());
        info.put("isbn", book.getIsbn());
        info.put("author", book.author());
        info.put("language", book.getLanguage());
        info.put("physicalCondition", "new");
        info.put("locationPC", "1425");
        info.put("location", "CABA");
        info.put("province", "CABA");
        info.put("country", "Argentina");

        return info;
    }

    public List<AssetInstance> getAllAssetsInstances(){
        List<AssetInstance> books = new ArrayList<>();
        books.add(new AssetInstanceImpl(0, new BookImpl("12324", "Phill Knight", "Shoe Dog", "English"), PhysicalCondition.ASNEW, new UserImpl("a@a.com", "ippo", "hola"), new LocationImpl("1234", "vil", "BS", "AR")));
        books.add(new AssetInstanceImpl(0, new BookImpl("12324", "Knight", "Biblia", "English"), PhysicalCondition.ASNEW, new UserImpl("a@a.com", "ippo", "hola"), new LocationImpl("1234", "vil", "BS", "AR")));
        books.add(new AssetInstanceImpl(0, new BookImpl("12324", "Phill ", "og", "English"), PhysicalCondition.ASNEW, new UserImpl("a@a.com", "ippo", "hola"), new LocationImpl("1234", "vil", "BS", "AR")));
        books.add(new AssetInstanceImpl(0, new BookImpl("12324", "Evans", "DDD", "English"), PhysicalCondition.ASNEW, new UserImpl("a@a.com", "ippo", "hola"), new LocationImpl("1234", "vil", "BS", "AR")));
        return books;
    }

}
