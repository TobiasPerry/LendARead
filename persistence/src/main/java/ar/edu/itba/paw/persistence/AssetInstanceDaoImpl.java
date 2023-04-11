package ar.edu.itba.paw.persistence;

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
        return null;
    }
}
