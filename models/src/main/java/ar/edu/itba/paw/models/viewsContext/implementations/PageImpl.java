package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;

import java.util.List;

public class PageImpl implements Page {

    private final List<AssetInstance> books;
    private final int currentPage, totalPages;

    public PageImpl(List<AssetInstance> books, int currentPage, int totalPages){
        this.books = books;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    @Override
    public List<AssetInstance> getBooks() {
        return books;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }
}
