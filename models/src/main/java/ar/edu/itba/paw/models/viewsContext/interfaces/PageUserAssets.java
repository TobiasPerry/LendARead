package ar.edu.itba.paw.models.viewsContext.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;

import java.util.List;

public interface PageUserAssets extends AbstractPage{
    List<? extends AssetInstanceImpl> getUserAssets();
}
