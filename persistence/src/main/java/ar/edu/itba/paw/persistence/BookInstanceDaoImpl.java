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

    private static final RowMapper<AssetInstanceImpl>ROW_MAPPER = (rs,rownum)-> new AssetInstanceImpl(new BookImpl(rs.getInt("uId"),rs.getString("isbn"),rs.getString("author"),rs.getString("title"),rs.getString("lenguage"),rs.getBytes("photo")),PhysicalCondition.valueOf(rs.getString("physicalCondition")),new UserImpl(),new LocationImpl());

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
    public Optional<Integer> addAssetInstance(final AssetInstance ai)
    {

        return Optional.empty();
    }

}
