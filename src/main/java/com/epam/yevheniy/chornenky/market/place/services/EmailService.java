package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.exceptions.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Service for sending letters
 */
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

    /**
     *
     * @return properties of session
     */
    private Properties getPropertiesForSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return props;
    }

    /**
     *
     * @return authenticator for session. contains email sender and psw to sender email.
     */
    private Authenticator getAuthenticatorForSession() {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
    }

    /**
     * Send recipient latter. Body contains text of latter.
     * @param body latter
     */
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
}
