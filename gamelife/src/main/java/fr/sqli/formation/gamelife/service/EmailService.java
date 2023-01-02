package fr.sqli.formation.gamelife.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    private static final Logger LOG = LogManager.getLogger();
    @Autowired
    private JavaMailSender emailSender;


    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@gamelife.com", "GameLife Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        emailSender.send(message);
    }
    public void sendEmailValidationInscription(String recipientEmail, String link)throws MessagingException, UnsupportedEncodingException{
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@gamelife.com", "GameLife Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to activate your account";

        String content = "<p>Hello,</p>"
                + "<p>You have created an account on our site.</p>"
                + "<p>Click the link below to activate your account:</p>"
                + "<p><a href=\"" + link + "\">Activate my account</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do not made the request.</p> ";

        helper.setSubject(subject);

        helper.setText(content, true);

        emailSender.send(message);
    }
}
