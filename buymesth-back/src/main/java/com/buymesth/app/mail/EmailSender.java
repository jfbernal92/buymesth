package com.buymesth.app.mail;

import com.buymesth.app.models.ConfirmationToken;
import com.buymesth.app.models.PasswordRecovery;
import com.buymesth.app.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class EmailSender {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public ConfirmationTokenRepository confirmationTokenRepository;


    //TODO: plantear como enviar otro parametro de forma eficaz ej: id encyptada

    public void sendConfirmationEmail(Long id, ConfirmationToken t, String email, boolean firstTime) throws MessagingException {
        EmailConfirmationTemplate e = new EmailConfirmationTemplate(t.getToken(), String.valueOf(t.getCreatedDate().getTime()), encodeId(t.getCreatedDate(), id));
        MimeMessage message = emailSender.createMimeMessage();
        message.setRecipients(Message.RecipientType.TO, email);
        if (firstTime) {
            message.setSubject("Buymesth: Registration Successfully!");
            message.setContent(e.getTemplate(), "text/html");
        } else {
            message.setSubject("Buymesth: Confirmation link Resent");
            message.setContent(e.getTemplateAnotherLink(), "text/html");
        }
        this.emailSender.send(message);

    }

    public void sendPasswordRecoveryLinkEmail(PasswordRecovery pwd) throws MessagingException {
        EmailPasswordRecoveryTemplate e = new EmailPasswordRecoveryTemplate(pwd.getToken(), String.valueOf(pwd.getCreatedDate().getTime()), encodeId(pwd.getCreatedDate(), pwd.getId()));
        MimeMessage message = emailSender.createMimeMessage();
        message.setRecipients(Message.RecipientType.TO, pwd.getUser().getEmail());
        message.setSubject("Are you trying to reset your password?");
        message.setContent(e.getTemplate(), "text/html");
        this.emailSender.send(message);
    }

    private String encodeId(Date createdDate, Long id) {
        return String.valueOf(createdDate.getTime() + TimeUnit.DAYS.toMillis(19) + id);
    }

    public Long decodeId(Long d, Long id) {
        return id - d - TimeUnit.DAYS.toMillis(19);
    }
}
