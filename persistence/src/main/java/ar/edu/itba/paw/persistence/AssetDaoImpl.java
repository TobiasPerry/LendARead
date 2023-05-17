package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AssetDaoImpl implements AssetDao {

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Book> ROW_MAPPER = (rs, rownum) -> new BookImpl(rs.getInt("uid"), rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("lang"));

    @Autowired
    public AssetDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("book").usingGeneratedKeyColumns("uid");
    }


    @Override
    public Optional<List<Book>> getAssets() {
        final List<Book> bookList = jdbcTemplate.query("SELECT * FROM assetinstance", ROW_MAPPER);
        if (bookList.isEmpty())
            return Optional.empty();
        return Optional.of(bookList);
    }


    @Override
    public Book addAsset(final Book ai) throws BookAlreadyExistException {

        final Map<String, Object> args = new HashMap<>();
        args.put("isbn", ai.getIsbn());
        args.put("title", ai.getName());
        args.put("author", ai.getAuthor());
        args.put("lang", ai.getLanguage());
        int id;
        try {
            id = jdbcInsert.executeAndReturnKey(args).intValue();
        } catch (DuplicateKeyException e) {
            throw new BookAlreadyExistException();
        }
        return new BookImpl(id, ai.getIsbn(), ai.getAuthor(), ai.getName(), ai.getLanguage());
    }

    @Override
    public Optional<Book> getBook(String isbn) {
        Object[] params = new Object[]{isbn};
        Book book;
        try {
            book = jdbcTemplate.queryForObject("SELECT uid,isbn, author, title, lang FROM book where isbn = ?", params, ROW_MAPPER);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(book);
    }

}
