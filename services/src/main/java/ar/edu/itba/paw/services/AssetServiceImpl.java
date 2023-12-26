package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AssetService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetDao ad;

    @Autowired
    public AssetServiceImpl(final AssetDao ad) {
        this.ad = ad;
    }



    @Transactional(readOnly = true)
    @Override
    public List<Asset> getBooks(String isbn, String author, String title, String language) {
        return ad.getBooks(isbn, author, title, language);
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
    public Asset getBookById(int id) {
        return ad.getBookById(id);
    }
}
