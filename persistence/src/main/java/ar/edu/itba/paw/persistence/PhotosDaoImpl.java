package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.PhotosDao;
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
public class PhotosDaoImpl implements PhotosDao {



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PhotosDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public Optional<Integer> addPhoto(byte[] photo) {
        String query = "INSERT INTO photos(photo)VALUES(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conexion -> {
            PreparedStatement pstmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setBytes(1, photo);
            return pstmt;
        }, keyHolder);

        if(keyHolder.getKeys() == null){
            return Optional.empty();
        }else {
            int generatedId =(int) keyHolder.getKeys().get("id");
            return Optional.of(generatedId);
        }
    }

    @Override
    public Optional<Integer> getPhoto(int id) {
        return Optional.empty();
    }
}
