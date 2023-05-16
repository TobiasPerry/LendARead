package ar.edu.itba.paw.models.viewsContext.implementations;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;

import java.util.List;

public class PageUserAssetsImpl implements PageUserAssets {

    private final List<? extends AssetInstance> userAssets;
    private  int currentPage;
    private  int totalPages;

    public PageUserAssetsImpl(List<? extends AssetInstance> userAssets) {
        this.userAssets = userAssets;
    }

    public PageUserAssetsImpl(List<? extends AssetInstance> userAssets, int currentPage, int totalPages) {
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
    public List<? extends AssetInstance> getUserAssets() {
        return userAssets;
    }
}
