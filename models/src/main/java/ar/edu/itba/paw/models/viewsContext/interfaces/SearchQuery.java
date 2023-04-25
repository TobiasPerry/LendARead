package ar.edu.itba.paw.models.viewsContext.interfaces;

import java.util.List;

public interface SearchQuery {

    List<String> getAuthors();

    List<String> getLanguages();

    List<String> getPhysicalConditions();
}
