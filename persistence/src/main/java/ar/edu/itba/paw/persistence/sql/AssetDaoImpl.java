package ar.edu.itba.paw.persistence.sql;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.itba.edu.paw.exceptions.BookAlreadyExistException;
import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository
public class AssetDaoImpl implements AssetDao {

    private final SimpleJdbcInsert jdbcInsert;
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<BookImpl> ROW_MAPPER = (rs, rownum) -> new BookImpl(rs.getInt("uid"), rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("lang"));

    @Autowired
    public AssetDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("book").usingGeneratedKeyColumns("uid");
    }


    @Override
    public Optional<List<BookImpl>> getAssets() {
        final List<BookImpl> bookList = jdbcTemplate.query("SELECT * FROM assetinstance", ROW_MAPPER);
        if (bookList.isEmpty())
            return Optional.empty();
        return Optional.of(bookList);
    }


    @Override
    public BookImpl addAsset(final BookImpl book) throws BookAlreadyExistException {

        final Map<String, Object> args = new HashMap<>();
        args.put("isbn", book.getIsbn());
        args.put("title", book.getName());
        args.put("author", book.getAuthor());
        args.put("lang", book.getLanguage());
        int id;
        try {
            id = jdbcInsert.executeAndReturnKey(args).intValue();
        } catch (DuplicateKeyException e) {
            throw new BookAlreadyExistException();
        }
        return new BookImpl(id, book.getIsbn(), book.getAuthor(), book.getName(), book.getLanguage());
    }

    @Override
    public Optional<BookImpl> getBook(String isbn) {
        Object[] params = new Object[]{isbn};
        BookImpl book;
        try {
            book = jdbcTemplate.queryForObject("SELECT uid,isbn, author, title, lang FROM book where isbn = ?", params, ROW_MAPPER);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(book);
    }

}
