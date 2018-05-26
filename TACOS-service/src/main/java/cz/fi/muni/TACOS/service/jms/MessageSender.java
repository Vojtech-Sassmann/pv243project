package cz.fi.muni.TACOS.service.jms;

import cz.fi.muni.TACOS.service.Resources;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@ApplicationScoped
public class MessageSender {

    private final Logger log = Logger.getLogger(MessageSender.class.getName());

    @Resource(mappedName = Resources.ASYNC_QUEUE)
    private Queue queue;

    @Inject
    JMSContext context;

    public void sendMessage(Serializable object) {
        try {
            context.createProducer().send(queue, object);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to send message.", e);
        }
    }
}
