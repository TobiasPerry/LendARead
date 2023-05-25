package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Book;
import ar.edu.itba.paw.models.userContext.interfaces.Location;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine templateEngine;


    private final String baseUrl;

    private final MessageSource messageSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);

    @Autowired
    public EmailServiceImpl(final JavaMailSender javaMailSender, final SpringTemplateEngine templateEngine, final @Qualifier("baseUrl") String baseUrl, final MessageSource messageSource) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.baseUrl = baseUrl;
        this.messageSource = messageSource;

    }

    private void sendEmail(final String addressTo, final String subject, final String message) {
        LOGGER.debug("Sending email to {}", addressTo);
        MimeMessage msj = javaMailSender.createMimeMessage();
        try {
            msj.setFrom(new InternetAddress("lendabookservice@gmail.com"));
            msj.setRecipient(Message.RecipientType.TO, new InternetAddress(addressTo));
            msj.setSubject(subject);
            msj.setContent(message, "text/html");
            javaMailSender.send(msj);
        } catch (MessagingException e) {
            LOGGER.error("Failed to send email to {}", addressTo);
        }
    }

    @Async
    @Override
    public void sendLenderEmail(final AssetInstanceImpl assetInstance, final String borrower, final int lendingId) {
        if (assetInstance == null || borrower == null) {
            return;
        }
        Map<String, Object> variables = new HashMap<>();
        User owner = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Book book = assetInstance.getBook();
        variables.put("book", book);
        variables.put("borrower", borrower);
        variables.put("owner", owner);
        variables.put("location", location);
        variables.put("path", baseUrl + "lentBookDetails/" + lendingId);

        String email = owner.getEmail();
        String bookName = book.getName();
        String subject = String.format(messageSource.getMessage("email.lender.subject", null, LocaleContextHolder.getLocale()), bookName);
        this.sendEmail(email, subject, this.mailFormat(variables, "lenderEmailTemplate.html"));
    }

    @Async
    @Override
    public void sendBorrowerEmail(final AssetInstanceImpl assetInstance, final User borrower, final int lendingId) {
        if (assetInstance == null || borrower == null) {
            return;
        }
        Book book = assetInstance.getBook();
        User owner = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Map<String, Object> variables = new HashMap<>();
        variables.put("book", book);
        variables.put("borrower", borrower.getName());
        variables.put("owner", owner);
        variables.put("path", baseUrl + "borrowedBookDetails/" + lendingId);
        variables.put("location", location);
        String subject = String.format(messageSource.getMessage("email.borrower.subject", null, LocaleContextHolder.getLocale()), assetInstance.getBook().getName());

        this.sendEmail(borrower.getEmail(), subject, this.mailFormat(variables, "borrowerEmailTemplate.html"));
    }

    @Async
    @Override
    public void sendForgotPasswordEmail(final String email, final String token) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("token", token);
        this.sendEmail(email, messageSource.getMessage("email.verificationcode.title", null, LocaleContextHolder.getLocale()), this.mailFormat(variables, "ForgotPasswordEmailTemplate.html"));
    }

    private String mailFormat(final Map<String, Object> variables, final String mailTemplate) {
        Context thymeleafContext = new Context(LocaleContextHolder.getLocale());
        ;
        thymeleafContext.setVariables(variables);
        return templateEngine.process(mailTemplate, thymeleafContext);
    }

}
