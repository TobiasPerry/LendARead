package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.BookInstanceDao;
import models.assetExistanceContext.implementations.AssetInstanceImpl;
import models.assetExistanceContext.implementations.BookImpl;
import models.assetExistanceContext.implementations.PhysicalCondition;
import models.assetExistanceContext.interfaces.AssetInstance;
import models.assetExistanceContext.interfaces.Book;
import models.userContext.implementations.LocationImpl;
import models.userContext.implementations.UserImpl;
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

    private static final RowMapper<AssetInstanceImpl>ROW_MAPPER = (rs,rownum)-> new AssetInstanceImpl(new BookImpl(rs.getString("isbn"),rs.getString("author"),rs.getString("title"),rs.getString("lenguage")),PhysicalCondition.valueOf(rs.getString("physicalCondition")),new UserImpl(),new LocationImpl());

    @Autowired
    public BookInstanceDaoImpl(final DataSource	ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public Optional<List<AssetInstanceImpl>> getAvailableBooks() {
        final List<AssetInstanceImpl> bookList = jdbcTemplate.query("SELECT * FROM bookinstance",ROW_MAPPER);
        if(bookList.isEmpty())
            return Optional.empty();
        return Optional.of(bookList);
    }


    @Override
    public Optional<Integer> addAssetInstance(final Book ai)
    {
        //TODO fijarse como hacer de devolver el id o de devolver algo
        jdbcTemplate.update("INSERT INTO book(isbn, title,author,language,photo) SELECT ? , ?,'?','?','?'  WHERE NOT EXISTS (SELECT isbn FROM book WHERE isbn = ?)", ai.getIsbn(), ai.getName(),ai.author(),ai.getLanguage(),ai.getPhoto(), ai.getIsbn());
        return Optional.empty();
    }

}
