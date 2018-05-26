package cz.fi.muni.TACOS.service;

import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;

/**
 * Application scoped JMS resources for the samples.
 * @author Patrik Dudits
 */
@JMSDestinationDefinitions({
    @JMSDestinationDefinition(name = Resources.ASYNC_QUEUE,
        resourceAdapter = "jmsra",
        interfaceName = "javax.jms.Queue",
        destinationName = "orderQueue",
        description = "My Async Queue"),
})
public class Resources {
    public static final String ASYNC_QUEUE = "java:jboss/exported/jms/queue/order";
}
