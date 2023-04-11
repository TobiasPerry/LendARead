package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.LocationDao;
import models.userContext.interfaces.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class LocationDaoImpl implements LocationDao {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public LocationDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }


    @Override
    public Optional<Integer> addLocation(Location lc) {
        String query = "INSERT INTO location(zipcode,locality,province,country,address) VALUES(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conexion -> {
            PreparedStatement pstmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, lc.getZipcode());
            pstmt.setString(2, lc.getLocality());
            pstmt.setString(3, lc.getProvince());
            pstmt.setString(4, lc.getCountry());
            pstmt.setString(5, lc.getAddress());
            return pstmt;
        }, keyHolder);
        if(keyHolder.getKeys() == null){
            return Optional.empty();
        }else {
            int generatedId =(int) keyHolder.getKeys().get("id");
            return Optional.of(generatedId);
        }
    }




}
