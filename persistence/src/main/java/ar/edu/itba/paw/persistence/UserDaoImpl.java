package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import models.userContext.interfaces.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Integer> ROW_MAPPER_ID = (rs, rownum) -> rs.getInt("id");

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public Optional<Integer> addUser(User us) {
        String query = "INSERT INTO users(behavior,mail,telephone) VALUES(?,?,?) ON CONFLICT DO NOTHING RETURNING id";


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conexion) throws SQLException {
                PreparedStatement pstmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, "lender");
                pstmt.setString(2,us.getEmail());
                pstmt.setString(3,us.getTelephone());

                return pstmt;
            }
        }, keyHolder);
        if(keyHolder.getKeys() == null){
            return getUid(us);
        }else {
            int generatedId =(int) keyHolder.getKeys().get("id");
            return Optional.of(generatedId);
        }
    }
    private Optional<Integer> getUid(User us){
        String query = "SELECT id FROM users WHERE mail = ?";
        final List<Integer> ids = jdbcTemplate.query(query,new Object[]{us.getEmail()},ROW_MAPPER_ID);
        if(ids.isEmpty())
            return Optional.empty();
        return Optional.of(ids.get(0));
    }
}
