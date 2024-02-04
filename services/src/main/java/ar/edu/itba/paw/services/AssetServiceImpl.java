package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetAlreadyExistException;
import ar.edu.itba.paw.exceptions.AssetNotFoundException;
import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.exceptions.UnableToCreateAssetException;
import ar.edu.itba.paw.interfaces.AssetService;
import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.assetExistanceContext.Language;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetDao ad;
    private final LanguagesService ls;

    @Autowired
    public AssetServiceImpl(final AssetDao ad, final LanguagesService ls) {
        this.ad = ad;
        this.ls = ls;
    }


    @Transactional(readOnly = true)
    @Override
    public PagingImpl<Asset> getAssets(final int page, final int itemsPerPage, final String isbn, final String author, final String title, final String language) {
        return ad.getBooks(page,itemsPerPage,isbn, author, title, language);
    }

    @Transactional
    @Override
    public Asset addAsset(String isbn, String author, String title, String languageId) throws AssetAlreadyExistException, UnableToCreateAssetException {
        try {
            Language language = ls.getLanguage(languageId).orElseThrow(UnableToCreateAssetException::new);
            return ad.addAsset(new Asset(isbn, author, title, language));
        }
        catch (BookAlreadyExistException e) {
            throw new AssetAlreadyExistException();
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Asset> getAssetById(Long id)  {
        return ad.getBookById(id);
    }

    @Transactional
    @Override
    public void updateAsset(Long id, String isbn, String author, String title, String language) throws AssetNotFoundException, LanguageNotFoundException, AssetAlreadyExistException {
        Asset asset = getAssetById(id).orElseThrow(AssetNotFoundException::new);
        Optional<Asset> assetWithSameIsbn = ad.getBookByIsbn(isbn);
        if (isbn != null) {
            if (assetWithSameIsbn.isPresent() && !assetWithSameIsbn.get().getId().equals(asset.getId())) {
                throw new AssetAlreadyExistException();
            }
            asset.setIsbn(isbn);
        }
        if (author != null) {
            asset.setAuthor(author);
        }
        if (title != null) {
            asset.setTitle(title);
        }
        if (language != null) {
            Language lang = ls.getLanguage(language).orElseThrow(LanguageNotFoundException::new);
            asset.setLanguage(lang);
        }
    }
}
