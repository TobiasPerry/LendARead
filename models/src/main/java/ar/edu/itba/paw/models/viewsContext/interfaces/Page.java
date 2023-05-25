package ar.edu.itba.paw.models.viewsContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

import java.util.List;

public interface Page extends AbstractPage {

    List<AssetInstanceImpl> getBooks();

    List<String> getAuthors();

    List<String> getLanguages();

    List<String> getPhysicalConditions();

}
