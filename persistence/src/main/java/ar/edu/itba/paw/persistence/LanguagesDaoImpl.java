package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Language;
import ar.itba.edu.paw.persistenceinterfaces.LanguageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LanguagesDaoImpl implements LanguageDao {
    private static final RowMapper<Language> ROW_MAPPER_LANG = (rs, rownum) -> new LanguageImpl(rs.getString("id"), rs.getString("name"));
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LanguagesDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<List<Language>> getLanguages() {
        String query = "SELECT id, name FROM languages";
        final List<Language> langs = jdbcTemplate.query(query, ROW_MAPPER_LANG);
        if (langs.isEmpty()) {
            return Optional.empty();
        }

        List<Language> langList = new ArrayList<>(langs);

        return Optional.of(langList);
    }
}
