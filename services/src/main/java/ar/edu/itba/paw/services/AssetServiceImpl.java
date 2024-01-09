package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.AssetNotFoundException;
import ar.edu.itba.paw.interfaces.AssetService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.edu.itba.paw.utils.HttpStatusCodes;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetDao ad;

    @Autowired
    public AssetServiceImpl(final AssetDao ad) {
        this.ad = ad;
    }



    @Transactional(readOnly = true)
    @Override
    public PagingImpl<Asset> getBooks(final int page, final int itemsPerPage, final String isbn, final String author, final String title, final String language) {
        return ad.getBooks(page,itemsPerPage,isbn, author, title, language);
    }

    @Transactional
    @Override
    public Asset addBook(String isbn, String author, String title, String language) throws BookAlreadyExistException {
        return ad.addAsset(new Asset(isbn, author, title, language));
    }

    @Transactional(readOnly = true)
    @Override
    public Asset getBookByIsbn(String isbn) {
        return ad.getBookByIsbn(isbn).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Asset getBookById(Long id) throws AssetNotFoundException {
        Asset asset =  ad.getBookById(id);
        if (asset == null) {
            throw new AssetNotFoundException(HttpStatusCodes.NOT_FOUND);
        }
        return asset;
    }
}
