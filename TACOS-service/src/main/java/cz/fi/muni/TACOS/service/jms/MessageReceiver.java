package cz.fi.muni.TACOS.service.jms;

import cz.fi.muni.TACOS.persistence.entity.Order;
import cz.fi.muni.TACOS.service.Impl.Email;
import cz.fi.muni.TACOS.service.Impl.EmailService;
import cz.fi.muni.TACOS.service.OrderService;
import cz.fi.muni.TACOS.service.Resources;
import cz.fi.muni.TACOS.service.jms.objects.OrderMessage;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = Resources.ASYNC_QUEUE),
        @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
})
public class MessageReceiver implements MessageListener {

    private final Logger log = Logger.getLogger(MessageReceiver.class.getName());

    @Inject
    private EmailService emailService;

    @Inject
    private OrderService orderService;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage om = (ObjectMessage) message;
            OrderMessage orderMessage = (OrderMessage) om.getObject();
            Order order = orderService.findById(orderMessage.getOrderId());
            System.out.println(order.getSubmitter().getEmail());
            System.out.println("Message received (async): " + orderMessage);

            Email customerEmail = new Email.Builder()
                    .setBody(orderMessage.getOrderEvent().getEmailGenerator().generateEmailBody(order))
                    .setFrom("tacos-shop2@gmail.com")
                    .setSubject("TACOS Order " + orderMessage.getOrderId())
                    .setTo(order.getSubmitter().getEmail())
                    .build();
            emailService.sendEmail(customerEmail);
        } catch (JMSException ex) {
            log.log(Level.SEVERE, null, ex);
        }
    }
}
