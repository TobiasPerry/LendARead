package ar.edu.itba.paw.models.viewsContext.interfaces;

import java.util.List;

public interface PageUserAssets<T> extends AbstractPage {
    List<T> getList();
}
