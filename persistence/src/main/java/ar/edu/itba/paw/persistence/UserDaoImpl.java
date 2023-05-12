package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.PasswordResetTokenImpl;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Integer> ROW_MAPPER_ID = (rs, rownum) -> rs.getInt("id");
    private static final RowMapper<User> ROW_MAPPER_USER =  (rs,rownum)->new UserImpl(rs.getInt("id"),rs.getString("mail"),rs.getString("name"), rs.getString("telephone"),rs.getString("password"), Behaviour.fromString(rs.getString("behavior")) );

    private static final RowMapper<PasswordResetToken> ROW_MAPPER_PASSWORD_TOKEN = (rs,rownum) ->new PasswordResetTokenImpl(rs.getString("token"),rs.getString("mail"),rs.getObject("expiration", LocalDate.class));
    @Autowired
    public UserDaoImpl(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    @Override
    public User addUser(Behaviour behavior,String email,String name,String telephone,String password) {
        String query = "INSERT INTO users(behavior,mail,telephone,name,password) VALUES(?,?,?,?,?)";


        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conexion -> {
            PreparedStatement pstmt = conexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, behavior.toString());
            pstmt.setString(2,email);
            pstmt.setString(3,telephone);
            pstmt.setString(4,name);
            pstmt.setString(5,password);
            return pstmt;
        }, keyHolder);
        if(keyHolder.getKeys() == null){
            return new UserImpl(getUid(email).orElse(-1),email,name,telephone,password,behavior);
        }else {
            int generatedId =(int) keyHolder.getKeys().get("id");
            return new UserImpl(generatedId,email,name,telephone,password,behavior);
        }
    }
    private Optional<Integer> getUid(String email){
        String query = "SELECT id FROM users WHERE mail = ?";
        final List<Integer> ids = jdbcTemplate.query(query,new Object[]{email},ROW_MAPPER_ID);
        if(ids.isEmpty())
            return Optional.empty();
        return Optional.of(ids.get(0));
    }
    @Override
    public Optional<User> getUser(String email){
        String query = "SELECT * FROM users WHERE mail = ?";
        final List<User> user = jdbcTemplate.query(query,ROW_MAPPER_USER,email);
        return user.stream().findFirst();
    }

    @Override
    public boolean changeRole(String email, Behaviour behaviour) {
        String query = "UPDATE users SET behavior = ? WHERE mail = ?";
        final int updates = jdbcTemplate.update(query,behaviour.toString(),email);
        if(updates != 0)
            return true;
        return false;
    }


    @Override
    public Optional<User> getUser(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        final List<User> user = jdbcTemplate.query(query,ROW_MAPPER_USER,id);
        return user.stream().findFirst();
    }

    @Override
    public boolean changePassword(String email,String newPassword){
        String query = "UPDATE users SET password = ? WHERE mail = ?";
        final int updates = jdbcTemplate.update(query,newPassword,email);
        return updates != 0;
    }
    @Override
    public boolean setForgotPasswordToken(PasswordResetToken passwordResetToken){
        String query = "INSERT INTO resetpasswordinfo(token,userid,expiration) VALUES(?,?,?)";
        Optional<User> user = getUser(passwordResetToken.getUser());
        if (!user.isPresent())
            return false;
        final int insert = jdbcTemplate.update(query,passwordResetToken.getToken(),user.get().getId(),passwordResetToken.getExpiryDate());
        return insert != 0;
    }

    @Override
    public Optional<PasswordResetToken> getPasswordRestToken(String token){
        String query = "SELECT * FROM resetpasswordinfo as s join users as u on s.userid = u.id WHERE s.token = ?";
        List<PasswordResetToken> passwordResetToken = jdbcTemplate.query(query,ROW_MAPPER_PASSWORD_TOKEN,token);
        return passwordResetToken.stream().findFirst();
    }

    public int deletePasswordRestToken(String token){
        String query = "DELETE FROM resetpasswordinfo where token = ?";
        final int delete = jdbcTemplate.update(query,token);
        return delete;
    }

}
