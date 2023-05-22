package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;

import java.util.List;
import java.util.Optional;

public class AssetDaoJpa implements AssetDao {
    @Override
    public Optional<List<Book>> getAssets() {
        return Optional.empty();
    }

    @Override
    public Book addAsset(Book bi) throws BookAlreadyExistException {
        return null;
    }

    @Override
    public Optional<Book> getBook(String isbn) {
        return Optional.empty();
    }
}
