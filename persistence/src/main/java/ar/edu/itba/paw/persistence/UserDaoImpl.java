package ar.edu.itba.paw.persistence;

import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import ar.edu.itba.paw.models.userContext.interfaces.User;
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
        String query = "INSERT INTO users(behavior,mail,telephone,name) VALUES(?,?,?,?) ON CONFLICT DO NOTHING RETURNING id";


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conexion -> {
            PreparedStatement pstmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, us.getBehavior());
            pstmt.setString(2,us.getEmail());
            pstmt.setString(3,us.getTelephone());
            pstmt.setString(4,us.getName());


            return pstmt;
        }, keyHolder);
        if(keyHolder.getKeys() == null){
            return getUid(us.getEmail());
        }else {
            int generatedId =(int) keyHolder.getKeys().get("id");
            return Optional.of(generatedId);
        }
    }
    private Optional<Integer> getUid(String email){
        String query = "SELECT id FROM users WHERE mail = ?";
        final List<Integer> ids = jdbcTemplate.query(query,new Object[]{email},ROW_MAPPER_ID);
        if(ids.isEmpty())
            return Optional.empty();
        return Optional.of(ids.get(0));
    }
    public Optional<Integer> getUser(String email){
        return getUid(email);
    }
    public boolean changePassword(String email,String newPassword){
        String query = "UPDATE users SET password = ? WHERE email = ?";
        final int updates = jdbcTemplate.update(query,email,newPassword);
        if(updates != 0)
            return true;
        return false;
    }
}
