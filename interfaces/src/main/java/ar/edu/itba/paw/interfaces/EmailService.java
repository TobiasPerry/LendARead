package ar.edu.itba.paw.interfaces;

public interface EmailService {
    public void sendEmail(final String addressTo, final String subject, final String message);
}
