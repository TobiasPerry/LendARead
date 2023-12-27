package ar.edu.itba.paw.models.viewsContext.interfaces;

public interface AbstractPage {

    int getCurrentPage();

    int getTotalPages();

    default boolean nextPage() {
        return  getCurrentPage() != getTotalPages();
    }

    default boolean previousPage() {
        return getCurrentPage() > 1;
    }

    default int getFirstPage() {return 1;}
}
