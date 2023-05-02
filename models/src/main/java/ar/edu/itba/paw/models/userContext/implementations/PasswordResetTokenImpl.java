package ar.edu.itba.paw.models.userContext.implementations;


import java.time.LocalDate;

public class PasswordResetTokenImpl implements ar.edu.itba.paw.models.userContext.interfaces.PasswordResetToken {

        private static final int EXPIRATION = 60 * 24;



        private final String token;


        private final String user;

        private final LocalDate expiryDate;

    public PasswordResetTokenImpl(String token, String user, LocalDate expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    @Override
    public String getToken() {
        return token;
    }
    @Override
    public String getUser() {
        return user;
    }
    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}

