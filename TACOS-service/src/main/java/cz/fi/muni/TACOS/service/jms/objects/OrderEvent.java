package cz.fi.muni.TACOS.service.jms.objects;

import cz.fi.muni.TACOS.persistence.entity.Order;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public enum OrderEvent {
    CREATED(order -> {
        StringBuilder body = new StringBuilder();
        body.append("We have received your order with number: ")
                .append(order.getId())
                .append("\n\n")
                .append("Purchased items: \n");

        order.getProducts().forEach(p -> {
            body.append("\t");
            body.append(p.getProduct().getName());
            body.append(": ");
            body.append(p.getCount());
            body.append(" piece(s)\n");
        });

        return body.toString();
    }),
    FINISHED(order -> "Your order with number: " +
            order.getId() +
            " has been finished."),
    CANCELLED(order -> "Your order with number: " +
            order.getId() +
            " has been cancelled."),
    PROCESSED(order -> "Your order with number: " +
            order.getId() +
            " is now being processed.");

    OrderEvent(EmailGenerator emailGenerator) {
        this.emailGenerator = emailGenerator;
    }

    private EmailGenerator emailGenerator;

    public EmailGenerator getEmailGenerator() {
        return emailGenerator;
    }

    @FunctionalInterface
    public interface EmailGenerator {
        String generateEmailBody(Order order);
    }
}
