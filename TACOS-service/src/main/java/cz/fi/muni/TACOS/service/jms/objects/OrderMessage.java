package cz.fi.muni.TACOS.service.jms.objects;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class OrderMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private long orderId;
    private OrderEvent orderEvent;

    public OrderMessage(long orderId, OrderEvent orderEvent) {
        this.orderId = orderId;
        this.orderEvent = orderEvent;
    }

    public long getOrderId() {
        return orderId;
    }

    public OrderEvent getOrderEvent() {
        return orderEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMessage that = (OrderMessage) o;
        return orderId == that.orderId &&
                orderEvent == that.orderEvent;
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, orderEvent);
    }

    @Override
    public String toString() {
        return "OrderMessage{" +
                "orderId=" + orderId +
                ", orderEvent=" + orderEvent +
                '}';
    }
}
