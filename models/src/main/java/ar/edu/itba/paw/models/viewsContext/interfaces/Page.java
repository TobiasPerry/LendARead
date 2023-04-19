package ar.edu.itba.paw.models.viewsContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

import java.util.List;

public interface Page {

    public List<AssetInstance> getBooks();

    public int getCurrentPage();

    public int getTotalPages();

}
