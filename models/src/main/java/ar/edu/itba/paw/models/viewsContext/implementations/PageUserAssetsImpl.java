package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

import java.util.List;

public class PageUserAssetsImpl<T> implements PageUserAssets<T> {

    private final List<T> userAssets;
    private  int currentPage;
    private  int totalPages;

    public PageUserAssetsImpl(List<T> userAssets) {
        this.userAssets = userAssets;
    }

    public PageUserAssetsImpl(List<T> userAssets, int currentPage, int totalPages) {
        this.userAssets = userAssets;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public List<T> getUserAssets() {
        return userAssets;
    }
}
