package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.OrderDao;
import cz.fi.muni.TACOS.persistence.entity.CreatedProduct;
import cz.fi.muni.TACOS.persistence.entity.Order;
import cz.fi.muni.TACOS.persistence.enums.OrderState;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.OrderService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
    public OrderServiceImpl(OrderDao orderDao) {
    	super(orderDao);

        this.orderDao = orderDao;
    }

	@Override
	public List<Order> getAllForState(OrderState state) {
		return orderDao.getAllForState(state);
	}

	@Override
	public void addProduct(Order order, CreatedProduct product) {
		if (order.getState() != OrderState.NEW) {
			throw new IllegalArgumentException("Can not add product to order that is not in NEW state: " + order.getState());
		}
		if (order.getProducts().contains(product)) {
			throw new IllegalArgumentException("Product is already in products for order");
		}
		order.addProduct(product);
	}

	@Override
	public void removeProduct(Order order, CreatedProduct product) {
		if (order.getState() != OrderState.NEW) {
			throw new IllegalArgumentException("Can not remove product from order that is not in NEW state: " + order.getState());
		}
		if (!order.getProducts().contains(product)) {
			throw new IllegalArgumentException("Product is not in products for order");
		}
		order.removeProduct(product);
	}

	@Override
	public void submitOrder(Order order) {
		if (order.getState() != OrderState.NEW) {
			throw new IllegalArgumentException("Product is not in New state: " + order.getState());
		}

		order.setSubmitted(LocalDate.now());
		order.setState(OrderState.SUBMITTED);
	}

	@Override
	public void cancelOrder(Order order) {
    	if (!(order.getState() == OrderState.NEW ||
				order.getState() == OrderState.SUBMITTED)) {
    		throw new IllegalArgumentException("Given order can not be canceled due to its state: " + order.getState());
		}

		order.setState(OrderState.CANCELED);
	}

	@Override
	public void finishOrder(Order order) {
    	if (!(order.getState() == OrderState.SUBMITTED) ||
				order.getState() == OrderState.PROCESSED) {
    		throw new IllegalArgumentException("Given order can not be finished due to its state: " + order.getState());
		}

		order.setState(OrderState.FINISHED);
	}

	@Override
	public void processOrder(Order order) {
		if (order.getState() != OrderState.SUBMITTED) {
			throw new IllegalArgumentException("Given order can not be processed due to its state: " + order.getState());
		}

		order.setState(OrderState.PROCESSED);
	}
}
