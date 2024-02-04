package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.models.assetExistanceContext.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.Asset;
import ar.edu.itba.paw.models.assetLendingContext.Lending;
import ar.edu.itba.paw.models.userContext.Location;
import ar.edu.itba.paw.models.userContext.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
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
import java.util.Locale;
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
    public void sendLenderEmail(final AssetInstance assetInstance, final String borrower, final Long lendingId, final Locale locale) {
        if (assetInstance == null || borrower == null) {
            return;
        }
        Map<String, Object> variables = new HashMap<>();
        User owner = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Asset book = assetInstance.getBook();
        variables.put("book", book);
        variables.put("borrower", borrower);
        variables.put("owner", owner);
        variables.put("location", location);
        variables.put("path", baseUrl + "userBook/" + lendingId+"?state=lended");

        String email = owner.getEmail();
        String bookName = book.getName();
        String subject = String.format(messageSource.getMessage("email.lender.subject", null, locale), bookName);
        this.sendEmail(email, subject, this.mailFormat(variables, "lenderEmailTemplate.html", locale));
    }

    @Async
    @Override
    public void sendBorrowerEmail(final AssetInstance assetInstance, final User borrower, final Long lendingId, final Locale locale) {
        if (assetInstance == null || borrower == null) {
            return;
        }
        Asset book = assetInstance.getBook();
        User owner = assetInstance.getOwner();
        Location location = assetInstance.getLocation();
        Map<String, Object> variables = new HashMap<>();
        variables.put("book", book);
        variables.put("borrower", borrower.getName());
        variables.put("owner", owner);
        variables.put("path", baseUrl + "userBook/" + lendingId+"?state=borrowed");
        variables.put("location", location);
        String subject = String.format(messageSource.getMessage("email.borrower.subject", null, locale), assetInstance.getBook().getName());

        this.sendEmail(borrower.getEmail(), subject, this.mailFormat(variables, "borrowerEmailTemplate.html", locale));
    }

    @Async
    @Override
    public void sendForgotPasswordEmail(final String email, final String token, final Locale locale) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("token", token);
        this.sendEmail(email, messageSource.getMessage("email.verificationcode.title", null, locale), this.mailFormat(variables, "ForgotPasswordEmailTemplate.html", locale));
    }

    @Async
    @Override
    public void sendRejectedEmail(AssetInstance assetInstance, User borrower, Long lendingId, Locale locale) {
        if (assetInstance == null || borrower == null) {
            return;
        }
        Asset book = assetInstance.getBook();
        Map<String, Object> variables = new HashMap<>();
        variables.put("book", book);
        variables.put("borrower", borrower);
        variables.put("path", baseUrl);
        String subject = messageSource.getMessage("email.rejected.subject", null, locale);
        this.sendEmail(borrower.getEmail(), subject, this.mailFormat(variables, "rejectedEmail.html", locale));
    }

    @Async
    @Override
    public void sendCanceledEmail(AssetInstance assetInstance, Long lendingId, Locale locale) {
        if (assetInstance == null) {
            return;
        }
        Asset book = assetInstance.getBook();
        User owner = assetInstance.getOwner();
        Map<String, Object> variables = new HashMap<>();
        variables.put("book", book);
        variables.put("lender", owner);
        variables.put("path", baseUrl);
        String subject = messageSource.getMessage("email.canceled.subject", null, locale);
        this.sendEmail(owner.getEmail(), subject, this.mailFormat(variables, "canceledEmail.html", locale));
    }

    @Async
    @Override
    public void sendReviewBorrower(AssetInstance assetInstance, User borrower, User lender, Long lendingId, Locale locale) {
        if (assetInstance == null || borrower == null) {
            return;
        }

        Asset book = assetInstance.getBook();
        Map<String, Object> variables = new HashMap<>();
        variables.put("assetInstance", assetInstance);
        variables.put("book", book);
        variables.put("borrower", borrower);
        variables.put("lender", lender);
        variables.put("path", baseUrl + "review/borrower/" + lendingId);
        String subject = messageSource.getMessage("email.review.borrower.subject", null, locale);
        LOGGER.debug("Sending email to borrower");
        this.sendEmail(borrower.getEmail(), subject, this.mailFormat(variables, "reviewBorrowerEmail.html", locale));
    }

    @Async
    @Override
    public void sendReviewLender(AssetInstance assetInstance, User lender, User borrower, Long lendingId, Locale locale) {
        if (assetInstance == null || lender == null) {
            return;
        }

        Asset book = assetInstance.getBook();
        Map<String, Object> variables = new HashMap<>();
        variables.put("assetInstance", assetInstance);
        variables.put("book", book);
        variables.put("borrower", borrower);
        variables.put("lender", lender);
        variables.put("path", baseUrl + "review/lender/" + lendingId);
        String subject = messageSource.getMessage("email.review.lender.subject", null, locale);
        LOGGER.debug("Sending email to lender");
        this.sendEmail(lender.getEmail(), subject, this.mailFormat(variables, "reviewLenderEmail.html", locale));
    }

    @Async
    @Override
    public void sendRemindLendingToLender(Lending lending, User lender, User borrower, Locale locale) {
        if (lending == null) {
            return;
        }
        Asset book = lending.getAssetInstance().getBook();
        Map<String, Object> variables = new HashMap<>();
        variables.put("lender", lender);
        variables.put("book", book);
        variables.put("borrower", borrower);
        variables.put("path", baseUrl);
        String subject = messageSource.getMessage("email.notify.newLending.lender.subject", null, locale);
        LOGGER.debug("Sending Reminder of new Lending to Lender");
        this.sendEmail(lender.getEmail(), subject, this.mailFormat(variables, "notifyLenderNewLending.html", locale));
    }

    @Async
    @Override
    public void sendRemindReturnToLender(Lending lending, User lender, User borrower, Locale locale) {
        if (lending == null) {
            return;
        }
        Asset book = lending.getAssetInstance().getBook();
        Map<String, Object> variables = new HashMap<>();
        variables.put("lender", lender);
        variables.put("book", book);
        variables.put("borrower", borrower);
        variables.put("path", baseUrl);
        String subject = messageSource.getMessage("email.notify.return.lender.subject", null, locale);
        LOGGER.debug("Sending Reminder of new Lending to Lender");
        this.sendEmail(lender.getEmail(), subject, this.mailFormat(variables, "notifyLenderReturn.html", locale));
    }

    private String mailFormat(final Map<String, Object> variables, final String mailTemplate, final Locale locale) {
        Context thymeleafContext = new Context(locale);
        thymeleafContext.setVariables(variables);
        return templateEngine.process(mailTemplate, thymeleafContext);
    }

}
