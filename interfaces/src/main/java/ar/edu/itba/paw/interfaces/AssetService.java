package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.AssetAlreadyExistException;
import ar.edu.itba.paw.exceptions.AssetNotFoundException;
import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToCreateAssetException;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;

import java.util.Optional;

public interface AssetService {

    PagingImpl<Asset> getAssets(final int page, final int itemsPerPage, final String isbn, final String author, final String title, final String language);
    Asset addAsset(final String isbn, final String author, final String title, final String language) throws AssetAlreadyExistException, UnableToCreateAssetException;
    Optional<Asset> getAssetById(final Long id) ;
    void updateAsset(final Long id, final String isbn, final String author, final String title, final String language) throws AssetNotFoundException, LanguageNotFoundException, AssetAlreadyExistException;
}
