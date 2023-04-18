package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
@Service
class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(final String addressTo, final String subject, final String message) {

        MimeMessage msj = javaMailSender.createMimeMessage();
        try {
            msj.setFrom(new InternetAddress("lendabookservice@gmail.com"));
            msj.setRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            msj.setSubject(subject);
            msj.setContent(message, "text/html");
            javaMailSender.send(msj);
        } catch (MessagingException e) {
            System.out.println("ERROR: Couldn't send email");
            System.err.println(e.getMessage());
        }
    }
}
