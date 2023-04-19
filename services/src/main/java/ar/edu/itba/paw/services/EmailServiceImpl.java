package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
@Service
class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine templateEngine;


    private final String baseUrl;
    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine,@Qualifier("baseUrl") String baseUrl) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.baseUrl = baseUrl;
    }

    @Override
    @Async
    public void sendEmail(final String addressTo, final String subject, final String message) {

        MimeMessage msj = javaMailSender.createMimeMessage();
        try {
            msj.setFrom(new InternetAddress("lendabookservice@gmail.com"));
            msj.setRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            msj.setSubject(subject);
            msj.setContent( message,"text/html");
            javaMailSender.send(msj);
        } catch (MessagingException e) {
            System.out.println("ERROR: Couldn't send email");
            System.err.println(e.getMessage());
        }
    }
    @Override
    public String lenderMailFormat(Map<String,Object> variables,String mailTemplate){
        Context thymeleafContext = new Context();
        variables.put("path",baseUrl);
        thymeleafContext.setVariables(variables);
        return templateEngine.process(mailTemplate, thymeleafContext);
    }
}
