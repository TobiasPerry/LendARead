package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.assetLendingContext.AssetState;
import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.List;

public class SearchQueryImpl implements SearchQuery {
    private final List<String> languages;
    private final List<String> physicalConditions;
    private final String search;
    private final AssetInstanceSort assetInstanceSort;
    private final SortDirection sortDirection;
    private final int minRating;
    private final int maxRating;
    private final int userId;

    private final AssetState assetState ;

    public SearchQueryImpl(List<String> languages, List<String>physicalConditions, String search, int minRating, int maxRating, int userId, AssetState assetState){
        this.languages = languages;
        this.physicalConditions = physicalConditions;
        this.search = search;
        this.assetInstanceSort = null;
        this.sortDirection = null;
        this.minRating = minRating;
        this.maxRating = maxRating;
        this.userId = userId;
        this.assetState = assetState;

    }

    public SearchQueryImpl(List<String> languages, List<String>physicalConditions, String search, AssetInstanceSort assetInstanceSort, SortDirection sortDirection, int minRating, int maxRating, int userId, AssetState assetState){
        this.languages = languages;
        this.physicalConditions = physicalConditions;
        this.search = search;
        this.assetInstanceSort = assetInstanceSort;
        this.sortDirection = sortDirection;
        this.minRating = minRating;
        this.maxRating = maxRating;
        this.userId = userId;
        this.assetState = assetState;

    }

    @Override
    public List<String> getLanguages() {
        return languages;
    }

    @Override
    public List<String> getPhysicalConditions() {
        return physicalConditions;
    }

    @Override
    public String getSearch(){
        return search;
    }

    @Override
    public AssetInstanceSort getSort() {
        return assetInstanceSort;
    }

    @Override
    public SortDirection getSortDirection() {
        return sortDirection;
    }

    @Override
    public int getMinRating(){
        return  minRating;
    }

    @Override
    public int getMaxRating(){
        return  maxRating;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public AssetState getAssetState() {
        return assetState;
    }

}
