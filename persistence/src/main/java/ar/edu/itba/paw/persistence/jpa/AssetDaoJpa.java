package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AssetDaoJpa implements AssetDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<List<Book>> getAssets() {
        TypedQuery<BookImpl> query = em.createQuery("SELECT b FROM BookImpl b", BookImpl.class);
        List<BookImpl> books = query.getResultList();
        return books.isEmpty() ? Optional.empty() : Optional.of(new ArrayList<>(books));
    }

    @Override
    public Book addAsset(Book bi) throws BookAlreadyExistException {
        final BookImpl book = new BookImpl(bi.getId(), bi.getIsbn(), bi.getAuthor(), bi.getName(), bi.getLanguage());
        Optional<Book> existingBook = getBook(book.getIsbn());
        if(existingBook.isPresent()){
            throw new BookAlreadyExistException();
        }
        em.persist(book);
        return book;
    }

    @Override
    public Optional<Book> getBook(String isbn) {
        TypedQuery<BookImpl> query = em.createQuery("SELECT b FROM BookImpl b WHERE b.isbn = :isbn", BookImpl.class);
        query.setParameter("isbn", isbn);
        List<BookImpl> books = query.getResultList();
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }
}

