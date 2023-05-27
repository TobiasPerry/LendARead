//package ar.edu.itba.paw.persistence.sql;
//
//import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;
//import ar.itba.edu.paw.persistenceinterfaces.LanguageDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//public class LanguagesDaoImpl implements LanguageDao {
//    private static final RowMapper<LanguageImpl> ROW_MAPPER_LANG = (rs, rownum) -> new LanguageImpl(rs.getString("id"), rs.getString("name"));
//    private final JdbcTemplate jdbcTemplate;
//
//    public LanguagesDaoImpl(final DataSource ds) {
//        this.jdbcTemplate = new JdbcTemplate(ds);
//    }
//
//    @Override
//    public Optional<List<LanguageImpl>> getLanguages() {
//        String query = "SELECT id, name FROM languages";
//        final List<LanguageImpl> langs = jdbcTemplate.query(query, ROW_MAPPER_LANG);
//        if (langs.isEmpty()) {
//            return Optional.empty();
//        }
//
//        List<LanguageImpl> langList = new ArrayList<>(langs);
//
//        return Optional.of(langList);
//    }
//}
