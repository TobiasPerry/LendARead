package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.AssetDao;
import models.assetExistanceContext.implementations.AssetInstanceImpl;
import models.assetExistanceContext.implementations.BookImpl;
import models.assetExistanceContext.implementations.PhysicalCondition;
import models.assetExistanceContext.interfaces.Book;
import models.userContext.implementations.LocationImpl;
import models.userContext.implementations.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class AssetDaoImpl implements AssetDao {


    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<AssetInstanceImpl>ROW_MAPPER = (rs,rownum)-> new AssetInstanceImpl(new BookImpl(rs.getString("isbn"),rs.getString("author"),rs.getString("title"),rs.getString("lenguage")),PhysicalCondition.valueOf(rs.getString("physicalCondition")),new UserImpl("","",""),new LocationImpl("","","",""));
    private static final RowMapper<Integer> ROW_MAPPER_UID = (rs,rownum) -> rs.getInt("uid");
    @Autowired
    public AssetDaoImpl(final DataSource	ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public Optional<List<AssetInstanceImpl>> getAssets() {
        final List<AssetInstanceImpl> bookList = jdbcTemplate.query("SELECT * FROM bookinstance",ROW_MAPPER);
        if(bookList.isEmpty())
            return Optional.empty();
        return Optional.of(bookList);
    }


    @Override
    public Optional<Integer> addAsset(final Book ai)
    {

        String query ="INSERT INTO book(isbn, title,author,language,photo) VALUES (?,?,?,?,?)  ON CONFLICT DO NOTHING RETURNING uid";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conexion) throws SQLException {
                PreparedStatement pstmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, ai.getIsbn());
                pstmt.setString(2,ai.getName());
                pstmt.setString(3,ai.author());
                pstmt.setString(4,ai.getLanguage());
                pstmt.setBytes(5,new byte[0]);
                return pstmt;
            }
        }, keyHolder);

        if(keyHolder.getKey() == null){
            return getUid(ai);
        }else {
            int generatedId = keyHolder.getKey().intValue();
            return Optional.of(generatedId);
        }
    }
    private Optional<Integer> getUid(final Book bi){
        final List<Integer> ids =  jdbcTemplate.query("SELECT uid FROM book WHERE isbn = ?",new Object[]{bi.getIsbn()},ROW_MAPPER_UID);
        if(ids.isEmpty())
            return Optional.empty();
        return Optional.of(ids.get(0));
    }


}
