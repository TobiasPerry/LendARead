package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;

import java.util.List;

public class PagingImpl<T> implements AbstractPage<T> {

    private final List<T> userAssets;
    private  int currentPage;
    private  int totalPages;

    public PagingImpl(List<T> userAssets) {
        this.userAssets = userAssets;
    }

    public PagingImpl(List<T> userAssets, int currentPage, int totalPages) {
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
    public List<T> getList() {
        return userAssets;
    }
}
