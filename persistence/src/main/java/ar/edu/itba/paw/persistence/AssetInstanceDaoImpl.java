package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.BookImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.assetLendingContext.implementations.AssetState;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
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

    private static final RowMapper<AssetInstance> ROW_MAPPER_BOOK = (rs, rownum) ->
            new AssetInstanceImpl(
                    rs.getInt(1),
                    new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language")),
                    PhysicalCondition.fromString(rs.getString("physicalcondition")),
                    new UserImpl(rs.getInt("owner"),rs.getString("mail"), "X", "X"),
                    new LocationImpl(rs.getInt("locationid"),rs.getString("zipcode"), rs.getString("locality"), rs.getString("province"), rs.getString("country")),
                    rs.getInt("photoId"),
                    AssetState.fromString(rs.getString("status"))
                    );
//new BookImpl(rs.getString("isbn"), rs.getString("author"), rs.getString("title"), rs.getString("language")
    @Autowired
    public AssetInstanceDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public Optional<Integer> addAssetInstance(int assetId,int ownerId,int locationId,int photoId, AssetInstance ai) {
        String query = "INSERT INTO assetinstance(assetid,owner,locationid,physicalcondition,status,photoid) VALUES(?,?,?,?,?,?)";


        jdbcTemplate.update(query,assetId,ownerId,locationId,ai.getPhysicalCondition().toString(),ai.getAssetState().name(),photoId);

        return Optional.empty();
    }

    @Override
    public Optional<List<AssetInstance>> getAllAssetInstances(){

        String query = "select * from assetinstance as ai join book as b on ai.assetid=b.uid join location as l on ai.locationid=l.id join users as u on ai.owner=u.id";

        List<AssetInstance> assets = jdbcTemplate.query(query, ROW_MAPPER_BOOK);

        return Optional.of(assets);
    }
}
