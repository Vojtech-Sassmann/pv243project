package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.OrderDao;
import cz.fi.muni.TACOS.persistence.entity.CreatedProduct;
import cz.fi.muni.TACOS.persistence.entity.Order;
import cz.fi.muni.TACOS.enums.OrderState;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.OrderService;
import cz.fi.muni.TACOS.service.jms.MessageSender;
import cz.fi.muni.TACOS.service.jms.objects.OrderEvent;
import cz.fi.muni.TACOS.service.jms.objects.OrderMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * EntityService layer for Order Entity
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@ApplicationScoped
public class OrderServiceImpl extends AbstractEntityService<Order> implements OrderService {

    private final OrderDao orderDao;

    @Inject
    private MessageSender messageSender;

    @Inject
    public OrderServiceImpl(OrderDao orderDao) {
    	super(orderDao);

        this.orderDao = orderDao;
    }

	@Override
	public List<Order> getAllForState(OrderState state) {
		return orderDao.getAllForState(state);
	}


	@Override
	public List<Order> getAllWithoutNewOrders() {
		return orderDao.getAllWithoutNewOrders();	}

	@Override
	public void addProduct(Order order, CreatedProduct product) {
		if (order.getState() != OrderState.BASKET) {
			throw new IllegalArgumentException("Can not add product to order that is not in BASKET state: " + order.getState());
		}
		order.addProduct(product);
		updatePrice(order);
	}

	@Override
	public void removeProduct(Order order, CreatedProduct product) {
		if (order.getState() != OrderState.BASKET) {
			throw new IllegalArgumentException("Can not remove product from order that is not in BASKET state: " + order.getState());
		}
		if (!order.getProducts().contains(product)) {
			throw new IllegalArgumentException("Product is not in products for order");
		}
		order.removeProduct(product);
		updatePrice(order);
	}

	@Override
	public void submitOrder(Order order) {
		if (order.getState() != OrderState.BASKET) {
			throw new IllegalArgumentException("Product is not in New state: " + order.getState());
		}

		order.setSubmitted(LocalDate.now());
		order.setState(OrderState.SUBMITTED);

		messageSender.sendMessage(new OrderMessage(order.getId(), OrderEvent.CREATED));
	}

	@Override
	public void cancelOrder(Order order) {
    	if (!(order.getState() == OrderState.BASKET ||
				order.getState() == OrderState.SUBMITTED)) {
    		throw new IllegalArgumentException("Given order can not be canceled due to its state: " + order.getState());
		}

		order.setState(OrderState.CANCELED);

		messageSender.sendMessage(new OrderMessage(order.getId(), OrderEvent.CANCELLED));
	}

	@Override
	public void finishOrder(Order order) {
    	if (!(order.getState() == OrderState.SUBMITTED || order.getState() == OrderState.PROCESSED)) {
    		throw new IllegalArgumentException("Given order can not be finished due to its state: " + order.getState());
		}
		order.setFinished(LocalDate.now());
		order.setState(OrderState.FINISHED);

		messageSender.sendMessage(new OrderMessage(order.getId(), OrderEvent.FINISHED));
	}

	@Override
	public void processOrder(Order order) {
		if (order.getState() != OrderState.SUBMITTED) {
			throw new IllegalArgumentException("Given order can not be processed due to its state: " + order.getState());
		}

		order.setState(OrderState.PROCESSED);

		messageSender.sendMessage(new OrderMessage(order.getId(), OrderEvent.PROCESSED));
	}

	/**
	 * Update order price after add/remove products
	 */
	private void updatePrice(Order order) {
		BigDecimal price = BigDecimal.ZERO;
		for (CreatedProduct product : order.getProducts()) {
			price = price.add(product.getPrice().multiply(BigDecimal.valueOf(product.getCount())));
		}

		order.setPrice(price);
	}
}
