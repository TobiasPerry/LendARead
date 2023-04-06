package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.BookInstanceDao;
import models.assetExistanceContext.implementations.BookImpl;
import models.assetExistanceContext.interfaces.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class BookInstanceDaoImpl implements BookInstanceDao {


    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Book>ROW_MAPPER = (rs,rownum)->new BookImpl(rs.getInt("uId"),rs.getString("isbn"),rs.getString("author"),rs.getString("title"),rs.getString("lenguage"),rs.getBytes("photo"));
    @Autowired
    public BookInstanceDaoImpl(final DataSource	ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public Optional<List<Book>> getAvailableBooks() {
        final List<Book> bookList = jdbcTemplate.query("SELECT * FROM bookinstance",ROW_MAPPER);
        if(bookList.isEmpty())
            return Optional.empty();
        return Optional.of(bookList);
    }
}
