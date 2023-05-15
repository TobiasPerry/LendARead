package ar.edu.itba.paw.persistence;
import ar.itba.edu.paw.persistenceinterfaces.LanguageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class LanguagesDaoImpl implements LanguageDao {
    private static final RowMapper<String> ROW_MAPPER_LANG = (rs, rownum)-> new StringBuilder(rs.getString("id")).append('#').append(rs.getString("name")).toString();
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LanguagesDaoImpl(final DataSource ds) {
       this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<HashMap<String, String>> getLanguages() {
        String query = "SELECT id, name FROM languages";
        final List<String> langs = jdbcTemplate.query(query, ROW_MAPPER_LANG);
        if (langs.isEmpty()) {
            return Optional.empty();
        }

        HashMap<String, String> resultMap = new HashMap<>();
        for (String row : langs) {
            String[] values = row.split("#");
            resultMap.put(values[0].trim(), values[1].trim());
        }

        return Optional.of(resultMap);
    }
}
