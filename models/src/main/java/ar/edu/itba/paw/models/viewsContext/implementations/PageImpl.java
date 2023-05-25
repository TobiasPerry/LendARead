package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;

import java.util.List;

public class PageImpl implements Page {

    private final List<AssetInstanceImpl> books;
    private final  List<String> authors, languages, physicalConditions;
    private final int currentPage, totalPages;

    public PageImpl(List<AssetInstanceImpl> books, int currentPage, int totalPages, List<String> authors, List<String> languages, List<String> physicalConditions){
        this.books = books;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.authors = authors;
        this.languages = languages;
        this.physicalConditions = physicalConditions;
    }

    @Override
    public List<AssetInstanceImpl> getBooks() {
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
}
