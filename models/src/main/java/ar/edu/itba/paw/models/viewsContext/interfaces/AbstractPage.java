package ar.edu.itba.paw.models.viewsContext.interfaces;

import java.util.List;

public interface AbstractPage<T> {

     List<T> getList();
    int getCurrentPage();

    int getTotalPages();

    default boolean nextPage() {
        return  getCurrentPage() < getTotalPages();
    }

    default boolean previousPage() {
        return getCurrentPage() > 1 ;
    }

    default int getFirstPage() {return 1;}
}
