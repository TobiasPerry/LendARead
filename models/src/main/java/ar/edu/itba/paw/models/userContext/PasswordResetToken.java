package ar.edu.itba.paw.models.userContext;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resetpasswordinfo")
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resetpasswordinfo_id_seq")
    @SequenceGenerator(sequenceName = "resetpasswordinfo_id_seq", name = "resetpasswordinfo_id_seq", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(length = 100,unique = true)
    private String token;

    @Column(length = 100,name = "userid")
    private int user;

    @Column(name = "expiration")
    private LocalDate expiryDate;

    public PasswordResetToken(String token, int user, LocalDate expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }
    public PasswordResetToken(){

    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return user;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}

