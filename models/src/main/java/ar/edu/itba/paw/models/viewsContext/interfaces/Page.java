package ar.edu.itba.paw.models.viewsContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstance;

import java.util.List;

public interface Page extends AbstractPage {

    List<AssetInstance> getBooks();

    List<String> getAuthors();

    List<String> getLanguages();

    List<String> getPhysicalConditions();

}
