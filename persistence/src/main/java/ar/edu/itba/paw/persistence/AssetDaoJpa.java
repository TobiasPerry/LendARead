package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.viewsContext.implementations.PagingImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AssetDaoJpa implements AssetDao {
    @PersistenceContext
    private EntityManager em;



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
    public PagingImpl<Asset> getBooks(final int page,final int itemPerPage,final String isbn,final String author,final String title,final String language) {
        StringBuilder sb = new StringBuilder("SELECT uid FROM book b ");
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
            sb.append("b.lang = :language ");
        }
        final int offset = (page - 1) * itemPerPage;
        String pagination = " LIMIT :limit OFFSET :offset ";
        final Query queryCount = em.createNativeQuery(sb.toString());
        sb.append(pagination);
        final Query queryNative  = em.createNativeQuery(sb.toString());

        if (isbn != null) {
            queryNative.setParameter("isbn", isbn);
            queryCount.setParameter("isbn", isbn);

        }
        if (author != null) {
            queryNative.setParameter("author", author);
            queryCount.setParameter("author", author);
        }
        if (title != null) {
            queryNative.setParameter("title", title);
            queryCount.setParameter("title", title);
        }
        if (language != null) {
            queryNative.setParameter("language", language);
            queryCount.setParameter("language", language);
        }
        queryNative.setParameter("limit", itemPerPage);
        queryNative.setParameter("offset", offset);
        @SuppressWarnings("unchecked")
        final List<Long> ids = (List<Long>) queryCount.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());
        final int totalPages = (int) Math.ceil((double) ((Number) ids.size()).longValue() / itemPerPage);

        @SuppressWarnings("unchecked")
        List<Long> list = (List<Long>) queryNative.getResultList().stream().map(
                n -> (Long) ((Number) n).longValue()).collect(Collectors.toList());

        // In case of empty result -> Return a Page with empty lists
        if (list.isEmpty())
            return new PagingImpl<>(Collections.emptyList(), page, totalPages);

        // Get the AssetInstances that match those IDs for given page
        final TypedQuery<Asset> query = em.createQuery("FROM Asset as a  WHERE a.id IN (:ids)" , Asset.class);
        query.setParameter("ids", list);
        List<Asset> assets = query.getResultList();

        return new PagingImpl<>(assets, page, totalPages);
    }

    @Override
    public Optional<Asset> getBookById(Long id) {
        return Optional.ofNullable(em.find(Asset.class, id));
    }

}

