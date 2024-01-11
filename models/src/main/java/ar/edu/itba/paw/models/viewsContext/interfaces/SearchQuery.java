package ar.edu.itba.paw.models.viewsContext.interfaces;

import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.viewsContext.implementations.AssetInstanceSort;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;

import java.util.List;

public interface SearchQuery {

    List<String> getLanguages();

    List<String> getPhysicalConditions();

    String getSearch();

    AssetInstanceSort getSort();

    SortDirection getSortDirection();

    int getMinRating();

    int getMaxRating();

    int getUserId();

    AssetState getAssetState();

}
