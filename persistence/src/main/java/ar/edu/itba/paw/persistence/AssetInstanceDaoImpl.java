package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.itba.edu.paw.persistenceinterfaces.AssetInstanceDao;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class AssetInstanceDaoImpl implements AssetInstanceDao {
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Integer> ROW_MAPPER_UID = (rs, rownum) -> rs.getInt("uid");

    private static final RowMapper<Book> ROW_MAPPER_BOOK = (rs, rownum) -> new AssetInstanceImpl(rs.getInt("ai.id"), new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language")), PhysicalCondition.fromString(rs.getString("ai.physicalcondition")), );
//new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language")
    @Autowired
    public AssetInstanceDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public Optional<Integer> addAssetInstance(int assetId,int ownerId,int locationId, AssetInstance ai) {
        String query = "INSERT INTO assetinstance(assetid,owner,locationid,physicalcondition,status) VALUES(?,?,?,?,?)";

        jdbcTemplate.update(query,assetId,ownerId,locationId,ai.getPhysicalCondition().toString(),"");


        return Optional.empty();
    }

    @Override
    public Optional<List<AssetInstance>> getAllAssetInstances(){
        String query = "SELECT * FROM ASSETINSTANCE ai JOIN BOOK b ON ai.assetid = b.uid";

        jdbcTemplate.query(query, ROW_MAPPER_BOOK);

        return null;
    }
}
