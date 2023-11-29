package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
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
    public Optional<List<BookImpl>> getAssets() {
        TypedQuery<BookImpl> query = em.createQuery("SELECT b FROM BookImpl b", BookImpl.class);
        List<BookImpl> books = query.getResultList();
        return books.isEmpty() ? Optional.empty() : Optional.of(new ArrayList<>(books));
    }

    @Override
    public BookImpl addAsset(BookImpl bi) throws BookAlreadyExistException {
        final BookImpl book = new BookImpl(bi.getId(), bi.getIsbn(), bi.getAuthor(), bi.getName(), bi.getLanguage());
        Optional<BookImpl> existingBook = getBookByIsbn(book.getIsbn());
        if (existingBook.isPresent()) {
            throw new BookAlreadyExistException();
        }
        em.persist(book);
        return book;
    }


    @Override
    public Optional<BookImpl> getBookByIsbn(final String isbn) {
        TypedQuery<BookImpl> query = em.createQuery("SELECT b FROM BookImpl b WHERE b.isbn = :isbn", BookImpl.class);
        query.setParameter("isbn", isbn);
        List<BookImpl> books = query.getResultList();
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<BookImpl> getBooks(String isbn, String author, String title, String language) {
        StringBuilder sb = new StringBuilder("SELECT b FROM BookImpl b ");
        boolean first = true;
        if (isbn != null) {
            sb.append("WHERE b.isbn = :isbn ");
            first = false;
        }
        if (author != null) {
            sb.append(first ? "WHERE " : "AND ");
            sb.append("b.author = :author ");
            first = false;
        }
        if (title != null) {
            sb.append(first ? "WHERE " : "AND ");
            sb.append("b.title = :title ");
            first = false;
        }
        if (language != null) {
            sb.append(first ? "WHERE " : "AND ");
            sb.append("b.language = :language ");
        }
        TypedQuery<BookImpl> query = em.createQuery(sb.toString(), BookImpl.class);
        if (isbn != null) {
            query.setParameter("isbn", isbn);
        }
        if (author != null) {
            query.setParameter("author", author);
        }
        if (title != null) {
            query.setParameter("title", title);
        }
        if (language != null) {
            query.setParameter("language", language);
        }
        return query.getResultList();
    }

    @Override
    public BookImpl getBookById(int id) {
        return em.find(BookImpl.class, id);
    }

}

