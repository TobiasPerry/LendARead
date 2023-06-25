package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.List;

public class SearchQueryImpl implements SearchQuery {
    private final List<String> languages;
    private final List<String> physicalConditions;
    private final String search;
    private final Sort sort;
    private final SortDirection sortDirection;
    private final int minRating;
    private final int maxRating;

    public SearchQueryImpl(List<String> languages, List<String>physicalConditions, String search, int minRating, int maxRating){
        this.languages = languages;
        this.physicalConditions = physicalConditions;
        this.search = search;
        this.sort = null;
        this.sortDirection = null;
        this.minRating = minRating;
        this.maxRating = maxRating;
    }

    public SearchQueryImpl(List<String> languages, List<String>physicalConditions, String search, Sort sort, SortDirection sortDirection, int minRating, int maxRating){
        this.languages = languages;
        this.physicalConditions = physicalConditions;
        this.search = search;
        this.sort = sort;
        this.sortDirection = sortDirection;
        this.minRating = minRating;
        this.maxRating = maxRating;
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
    public Sort getSort() {
        return sort;
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

}
