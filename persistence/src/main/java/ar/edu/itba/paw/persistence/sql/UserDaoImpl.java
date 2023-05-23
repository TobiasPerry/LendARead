package ar.edu.itba.paw.persistence.sql;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetTokenImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.itba.edu.paw.persistenceinterfaces.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<UserImpl> ROW_MAPPER_USER = (rs, rownum) -> new UserImpl(rs.getInt("id"), rs.getString("mail"), rs.getString("name"), rs.getString("telephone"), rs.getString("password"), Behaviour.fromString(rs.getString("behavior")));

    private static final RowMapper<PasswordResetToken> ROW_MAPPER_PASSWORD_TOKEN = (rs, rownum) -> new PasswordResetTokenImpl(rs.getString("token"), rs.getString("mail"), rs.getTimestamp("expiration").toLocalDateTime().toLocalDate());

    @Autowired
    public UserDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds).withTableName("users").usingGeneratedKeyColumns("id");

    }

    @Override
    public UserImpl addUser(Behaviour behavior, String email, String name, String telephone, String password) {
        final Map<String, Object> args = new HashMap<>();

        args.put("behavior", behavior);
        args.put("mail", email);
        args.put("telephone", telephone);
        args.put("password", password);
        args.put("name", name);

        int userId;
        userId = jdbcInsert.executeAndReturnKey(args).intValue();
        return new UserImpl(userId, email, name, telephone, password, behavior);

    }

    @Override
    public Optional<UserImpl> getUser(final String email) {
        String query = "SELECT * FROM users WHERE mail = ?";
        final List<UserImpl> user = jdbcTemplate.query(query, ROW_MAPPER_USER, email);
        return user.stream().findFirst();
    }

    @Override
    public boolean changeRole(final String email, final Behaviour behaviour) {
        String query = "UPDATE users SET behavior = ? WHERE mail = ?";
        final int updates = jdbcTemplate.update(query, behaviour.toString(), email);
        return updates != 0;
    }


    @Override
    public Optional<UserImpl> getUser(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        final List<UserImpl> user = jdbcTemplate.query(query, ROW_MAPPER_USER, id);
        return user.stream().findFirst();
    }

    @Override
    public boolean changePassword(String email, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE mail = ?";
        final int updates = jdbcTemplate.update(query, newPassword, email);
        return updates != 0;
    }

    @Override
    public boolean setForgotPasswordToken(PasswordResetToken passwordResetToken) {
        String query = "INSERT INTO resetpasswordinfo(token,userid,expiration) VALUES(?,?,?)";
        Optional<UserImpl> user = getUser(passwordResetToken.getUser());
        if (!user.isPresent())
            return false;
        final int insert = jdbcTemplate.update(query, passwordResetToken.getToken(), user.get().getId(), java.sql.Date.valueOf(passwordResetToken.getExpiryDate()));
        return insert != 0;
    }

    @Override
    public Optional<PasswordResetToken> getPasswordRestToken(String token) {
        String query = "SELECT * FROM resetpasswordinfo as s join users as u on s.userid = u.id WHERE s.token = ?";
        List<PasswordResetToken> passwordResetToken = jdbcTemplate.query(query, ROW_MAPPER_PASSWORD_TOKEN, token);
        return passwordResetToken.stream().findFirst();
    }

    @Override
    public int deletePasswordRestToken(String token) {
        String query = "DELETE FROM resetpasswordinfo where token = ?";
        final int delete = jdbcTemplate.update(query, token);
        return delete;
    }

}
