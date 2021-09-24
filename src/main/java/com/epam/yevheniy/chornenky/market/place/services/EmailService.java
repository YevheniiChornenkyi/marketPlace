package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    private static final Logger LOGGER = LogManager.getLogger(EmailService.class);

    private final String email;
    private final String password;
    private final String smtpHost;
    private final String port;
    private final Session session;

    public EmailService(String email, String password, String smtpHost, String port) {
        this.email = email;
        this.password = password;
        this.smtpHost = smtpHost;
        this.port = port;
        session = Session.getInstance(getPropertiesForSession(), getAuthenticatorForSession());
    }

    private Properties getPropertiesForSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost); //SMTP Host
        props.put("mail.smtp.port", port); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        return props;
    }

    private Authenticator getAuthenticatorForSession() {
        return new Authenticator() {
            //override the getPasswordAuthentication method
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
    }

    public void sendEmail(String recipient, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setSubject(subject);
            message.setText(body);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.error("Incorrect email, or message does not exist", e);
            throw new EmailException();
        }
    }

    public static void main(String[] args) throws MessagingException {
        EmailService emailService = new EmailService("market.place.toolz@gmail.com", "market@place",
                "smtp.gmail.com", "587");

        emailService.sendEmail("evgeny.chornenky@gmail.com", "epam", "trololo");
    }
}
