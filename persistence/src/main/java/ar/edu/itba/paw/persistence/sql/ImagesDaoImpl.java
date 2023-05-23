package ar.edu.itba.paw.persistence.sql;

import ar.itba.edu.paw.persistenceinterfaces.ImagesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImagesDaoImpl implements ImagesDao {


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;


    @Autowired
    public ImagesDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("photos").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Integer> addPhoto(byte[] photo) {

        final Map<String, Object> args = new HashMap<>();
        args.put("photo", photo);

        return Optional.of(jdbcInsert.executeAndReturnKey(args).intValue());
    }

    @Override
    public Optional<byte[]> getPhoto(int id) {
        String query = "SELECT photo FROM photos WHERE id = ?";

        List<byte[]> photos = jdbcTemplate.query(query, (rs, colnum) -> rs.getBytes("photo"), id);

        return photos.stream().findFirst();
    }
}
