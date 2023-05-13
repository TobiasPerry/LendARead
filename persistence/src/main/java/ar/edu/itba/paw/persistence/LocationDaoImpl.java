package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public LocationDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("location").usingGeneratedKeyColumns("id");

    }


    @Override
    public Optional<Integer> addLocation(Location lc) {
        final Map<String, Object> args = new HashMap<>();

        args.put("zipcode",lc.getZipcode());
        args.put("locality",lc.getLocality());
        args.put("province",lc.getProvince());
        args.put("country",lc.getCountry());
        args.put("address",lc.getAddress());

        return Optional.of(jdbcInsert.executeAndReturnKey(args).intValue());
    }




}
