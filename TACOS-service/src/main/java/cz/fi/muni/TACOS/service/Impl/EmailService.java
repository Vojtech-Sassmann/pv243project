package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.service.Resources;

import javax.enterprise.context.ApplicationScoped;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ApplicationScoped
public class EmailService {

    private final Logger log = Logger.getLogger(EmailService.class.getName());

    @Resource(lookup = Resources.EMAIL_SESSION)
    private Session session;

    public void sendEmail(Email email) {
        try {
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email.getFrom()));
            Address toAddress = new InternetAddress(email.getTo());
            message.addRecipient(Message.RecipientType.TO ,toAddress);
            message.setSubject(email.getSubject());
            message.setContent(email.getBody(), "text/plain");
            System.out.println("Trying to send");
            Transport.send(message);
            System.out.println("Sent");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to send email: " + email, e);
        }
    }
}
