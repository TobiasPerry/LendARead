package ar.edu.itba.paw.models.userContext.interfaces;

import java.time.LocalDate;

public interface PasswordResetToken {
    String getUser();
    String getToken();
    LocalDate getExpiryDate();
}
