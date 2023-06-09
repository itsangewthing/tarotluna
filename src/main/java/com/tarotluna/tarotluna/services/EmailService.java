package com.tarotluna.tarotluna.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.tarotluna.tarotluna.EmailDetails;


@Service
public class EmailService {

    //@Value("${spring.mail.username}")
    private String sender;

    //@Value("${spring.mail.password}")
    private String password;

    private boolean isEnabled = false;
    public boolean getIsEnabled() { return isEnabled; }

    public boolean sendSimpleMail(EmailDetails details) {
        if(!isEnabled) return true;

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(details.getRecipient()));
            message.setSubject(details.getSubject());
            message.setText(details.getMsgBody());

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendEmail(EmailDetails email) {
        if(!isEnabled) return true;

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, password);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);   // true = multipart message
            helper.setTo(email.getRecipient());
            helper.setSubject(email.getSubject());
            helper.setFrom(sender, "MyRecipe");
            // true = text/html
            helper.setText(email.getMsgBody(), true);

            Transport.send(msg);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
