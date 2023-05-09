package ar.edu.itba.paw.models.viewsContext.interfaces;

import ar.edu.itba.paw.models.viewsContext.implementations.Sort;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;

import java.util.List;

public interface SearchQuery {

    List<String> getLanguages();

    List<String> getPhysicalConditions();

    String getSearch();

    Sort getSort();

    SortDirection getSortDirection();
}
