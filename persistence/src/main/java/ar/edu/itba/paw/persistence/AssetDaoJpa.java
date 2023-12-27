package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Asset;
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
    public Optional<List<Asset>> getAssets() {
        TypedQuery<Asset> query = em.createQuery("SELECT b FROM Asset b", Asset.class);
        List<Asset> books = query.getResultList();
        return books.isEmpty() ? Optional.empty() : Optional.of(new ArrayList<>(books));
    }

    @Override
    public Asset addAsset(Asset bi) throws BookAlreadyExistException {
        final Asset book = new Asset(bi.getId(), bi.getIsbn(), bi.getAuthor(), bi.getName(), bi.getLanguage());
        Optional<Asset> existingBook = getBookByIsbn(book.getIsbn());
        if (existingBook.isPresent()) {
            throw new BookAlreadyExistException();
        }
        em.persist(book);
        return book;
    }


    @Override
    public Optional<Asset> getBookByIsbn(final String isbn) {
        TypedQuery<Asset> query = em.createQuery("SELECT b FROM Asset b WHERE b.isbn = :isbn", Asset.class);
        query.setParameter("isbn", isbn);
        List<Asset> books = query.getResultList();
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<Asset> getBooks(String isbn, String author, String title, String language) {
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
        TypedQuery<Asset> query = em.createQuery(sb.toString(), Asset.class);
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
    public Asset getBookById(int id) {
        return em.find(Asset.class, id);
    }

}

