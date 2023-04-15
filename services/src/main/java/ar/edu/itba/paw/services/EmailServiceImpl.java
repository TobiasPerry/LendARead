package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {

    Properties props;
    Session session;
    String address;

    public void createSession(final String user, final String appPassword) {
        this.props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        this.address = user;

        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,appPassword);
            }
        });
    }

    public void sendEmail(final String addressTo, final String subject, final String message) {
        Message msj = new MimeMessage(this.session);
        try {
            msj.setFrom(new InternetAddress(this.address));
            msj.setRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            msj.setSubject(subject);
            msj.setText(message);
            Transport.send(msj);
        } catch (MessagingException e) {
            System.out.println("ERROR: Couldn't send email");
            System.err.println(e.getMessage());
        }
    }
}
