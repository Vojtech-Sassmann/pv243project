package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.OrderDTO;
import cz.fi.muni.TACOS.enums.OrderState;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.facade.CreatedProductFacade;
import cz.fi.muni.TACOS.facade.OrderFacade;
import cz.fi.muni.TACOS.rest.ApiUris;
import cz.fi.muni.TACOS.rest.exceptions.ResourceNotFoundException;
import cz.fi.muni.TACOS.rest.interceptors.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Path(ApiUris.URI_ORDERS)
@Stateless
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Inject
	private OrderFacade orderFacade;

	@Inject
	private CreatedProductFacade createdProductFacade;

	@DELETE
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public void deleteOrder(@PathParam("id") Long id) {
		log.debug("Rest delete Order with id ({})", id);

		try {
			orderFacade.delete(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Order not found.");
		}
	}

	@GET
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderDTO> getAll() {
		log.debug("Rest get all orders");

		return orderFacade.getAll();

	}

	@GET
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public OrderDTO findOrderById(@PathParam("id") Long id) {
		log.debug("Rest find Order by id({})", id);

		OrderDTO orderDTO = orderFacade.findById(id);

		if (orderDTO == null) {
			throw new ResourceNotFoundException("Order not found");
		}
		return orderDTO;
	}

	@GET
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filter/state/{state}")
	public List<OrderDTO> getAllForState(@PathParam("state") OrderState state) {
		log.debug("Rest get all orders for state ({})", state);

		if (state == null){
			throw new IllegalArgumentException("Invalid parameters given.");
		}

		return orderFacade.getAllForState(state);
	}

	@GET
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filter/notNew")
	public List<OrderDTO> getAllWithoutNewOrders() {
		log.debug("Rest get all orders in OrderState != BASKET");

		return orderFacade.getAllWithoutNewOrders();
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
			UserRole.SUBMITTER
	})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{orderId}/removeCreatedProduct")
	public void removeCreatedProduct(@PathParam("orderId") Long id, @QueryParam("createdProductId") Long createdProductId) {
		log.error("Rest remove CreatedProduct with id ({}) to Order with id ({})", id, createdProductId);

		if (orderFacade.findById(id) == null) {
			throw new ResourceNotFoundException("Order with id "+ id +" not found!");
		}
		if (createdProductFacade.findById(createdProductId) == null) {
			throw new ResourceNotFoundException("CreatedProduct with id "+ createdProductId +" not found!");
		}

		orderFacade.removeProduct(id, createdProductId);
	}


	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
			UserRole.SUBMITTER
	})
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/submitOrder")
	public void submitOrder(@QueryParam("id") Long id) {
		log.debug("Rest submit Order with id ({})", id);

		try {
			orderFacade.submitOrder(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Order not found");
		}
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
			UserRole.SUBMITTER
	})
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/cancelOrder")
	public void cancelOrder(@QueryParam("id") Long id) {
		log.debug("Rest cancel Order with id ({})", id);

		try {
			orderFacade.cancelOrder(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Order not found");
		}
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER
	})
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/finishOrder")
	public void finishOrder(@QueryParam("id") Long id) {
		log.debug("Rest finish Order with id ({})", id);

		try {
			orderFacade.finishOrder(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Order not found");
		}
	}

	@PUT
	@Secured(roles = {
			UserRole.SUPERADMIN,
			UserRole.PRACTITIONER,
	})
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/processOrder")
	public void processOrder(@QueryParam("id") Long id) {
		log.debug("Rest process Order with id ({})", id);

		try {
			orderFacade.processOrder(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Order not found");
		}
	}

}
