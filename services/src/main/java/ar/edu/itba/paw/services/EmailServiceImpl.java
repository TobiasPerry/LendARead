package ar.edu.itba.paw.services;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class EmailServiceImpl {
    public static void sendEmail(final String addressTo, final String subject, final String message) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lendabookservice@gmail.com","dijwfcejvnxeizvg");
            }
        });
        Message msj = new MimeMessage(session);
        try {
            msj.setFrom(new InternetAddress("lendabookservice@gmail.com"));
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
