package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.viewsContext.interfaces.SearchQuery;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.List;

public class SearchQueryImpl implements SearchQuery {

    private final List<String> authors;
    private final List<String> languages;
    private final List<String> physicalConditions;
    private final String search;

    public SearchQueryImpl(List<String> authors, List<String> languages, List<String>physicalConditions, String search){
        this.authors = authors;
        this.languages = languages;
        this.physicalConditions = physicalConditions;
        this.search = search;
    }

    @Override
    public List<String> getAuthors() {
        return authors;
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
}
