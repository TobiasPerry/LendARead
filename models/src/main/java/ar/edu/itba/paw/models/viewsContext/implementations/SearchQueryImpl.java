package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;

import java.util.List;

public class SearchQueryImpl implements SearchQuery {
    private final List<String> languages;
    private final List<String> physicalConditions;
    private final String search;
    private final Sort sort;
    private final SortDirection sortDirection;

    public SearchQueryImpl(List<String> languages, List<String>physicalConditions, String search){
        this.languages = languages;
        this.physicalConditions = physicalConditions;
        this.search = search;
        this.sort = null;
        this.sortDirection = null;
    }

    public SearchQueryImpl(List<String> languages, List<String>physicalConditions, String search, Sort sort, SortDirection sortDirection){
        this.languages = languages;
        this.physicalConditions = physicalConditions;
        this.search = search;
        this.sort = sort;
        this.sortDirection = sortDirection;
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


}
