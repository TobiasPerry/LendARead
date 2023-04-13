package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ImagesDaoImpl implements ImagesDao {



    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ImagesDaoImpl(final DataSource ds) {
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
    public Optional<byte []> getPhoto(int id) {
        String query = "SELECT photo FROM photos WHERE id = ?";

        List<byte[]> photos = jdbcTemplate.query(query,(rs, colnum)-> rs.getBytes("photo"),id);

        return photos.stream().findFirst();
    }
}
