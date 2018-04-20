package cz.fi.muni.TACOS.facade.impl;

import cz.fi.muni.TACOS.dto.OrderDTO;
import cz.fi.muni.TACOS.facade.OrderFacade;
import cz.fi.muni.TACOS.persistence.entity.Order;
import cz.fi.muni.TACOS.enums.OrderState;
import cz.fi.muni.TACOS.service.BeanMappingService;
import cz.fi.muni.TACOS.service.CreatedProductService;
import cz.fi.muni.TACOS.service.OrderService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of OrderFacade Interface
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Transactional(rollbackOn = { Exception.class })
@ApplicationScoped
public class OrderFacadeImpl implements OrderFacade {

	private final OrderService orderService;

	private final CreatedProductService createdProductService;

	private final BeanMappingService beanMappingService;

	@Inject
	public OrderFacadeImpl(OrderService orderService, CreatedProductService createdProductService,
						   BeanMappingService beanMappingService) {
		this.orderService = orderService;
		this.createdProductService = createdProductService;
		this.beanMappingService = beanMappingService;
	}

	@Override
	public void delete(Long id) {
		orderService.delete(orderService.findById(id));
	}

	@Override
	public OrderDTO findById(Long id) {
		Order order = orderService.findById(id);
		return beanMappingService.mapTo(order, OrderDTO.class);
	}

	@Override
	public List<OrderDTO> getAll() {
		return beanMappingService.mapTo(orderService.getAll(), OrderDTO.class);
	}

	@Override
	public List<OrderDTO> getAllForState(OrderState state) {
		return beanMappingService.mapTo(orderService.getAllForState(state), OrderDTO.class);
	}

	@Override
	public void removeProduct(Long orderId, Long productId) {
		orderService.removeProduct(orderService.findById(orderId), createdProductService.findById(productId));
	}

	@Override
	public void submitOrder(Long orderId) {
		orderService.submitOrder(orderService.findById(orderId));
	}

	@Override
	public void cancelOrder(Long orderId) {
		orderService.cancelOrder(orderService.findById(orderId));
	}

	@Override
	public void finishOrder(Long orderId) {
		orderService.finishOrder(orderService.findById(orderId));
	}

	@Override
	public void processOrder(Long orderId) {
		orderService.processOrder(orderService.findById(orderId));
	}
}
