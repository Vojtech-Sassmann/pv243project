package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.OrderDTO;
import cz.fi.muni.TACOS.dto.UserCreateDTO;
import cz.fi.muni.TACOS.dto.UserDTO;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.facade.OrderFacade;
import cz.fi.muni.TACOS.facade.UserFacade;
import cz.fi.muni.TACOS.rest.ApiUris;
import cz.fi.muni.TACOS.rest.exceptions.ResourceAlreadyExistException;
import cz.fi.muni.TACOS.rest.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path(ApiUris.URI_USERS)
@Stateless
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Inject
	private UserFacade userFacade;

	@Inject
	private OrderFacade orderFacade;


	/**
	 * curl -X POST -i -H "Content-Type: application/json" --data '{"name":"Name", "surname":"Surname", "email":"email@mail.com", "role":"SUBMITTER", "password":"123"}' http://localhost:8080/TACOS-rest/api/v1/users/create
	 *
	 * @param userCreateDTO
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO createUser(UserCreateDTO userCreateDTO) {
		log.debug("Rest create user ({})", userCreateDTO);

		UserDTO userDTO = userFacade.findByEmail(userCreateDTO.getEmail());
		if (userDTO != null) {
			throw new ResourceAlreadyExistException("User with given email is already existing.");
		}
		Long id = userFacade.create(userCreateDTO);

		return userFacade.findById(id);
	}

	/**
	 * curl -i -X DELETE http://localhost:8080/TACOS-rest/api/v1/users/delete/1
	 *
	 * @param id
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public void deleteUser(@PathParam("id") Long id) {
		log.debug("Rest delete user with id ({})", id);

		try {
			userFacade.delete(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("User not found.");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public UserDTO findUserById(@PathParam("id") Long id) {
		log.debug("Rest find User by id({})", id);

		UserDTO userDTO = userFacade.findById(id);

		if (userDTO == null) {
			throw new ResourceNotFoundException("User not found");
		}
		return userDTO;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filter/email/{email}")
	public UserDTO findUserByEmail(@PathParam("email") String email) {
		log.debug("Rest find User by email({})", email);

		UserDTO userDTO = userFacade.findByEmail(email);

		if (userDTO == null) {
			throw new ResourceNotFoundException("User not found");
		}
		return userDTO;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> getAll() {
		log.debug("Rest get all orders");

		return userFacade.getAll();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/filter/role/{role}")
	public List<UserDTO> getAllForRole(@PathParam("role") UserRole role) {
		log.debug("Rest get all orders with role ({})", role);

		if (role == null){
			throw new IllegalArgumentException("Invalid parameters given.");
		}

		return userFacade.getAllForRole(role);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userId}/addOrder/{orderId}")
	public void addOrdertoUser(@PathParam("userId") Long id, @PathParam("orderId") Long orderId) {
		log.debug("Rest add order with id ({}) to user with id ({})", id, orderId);

		if (userFacade.findById(id) == null) {
			throw new ResourceNotFoundException("User with id "+ id +" not found!");
		}
		if (orderFacade.findById(orderId) == null) {
			throw new ResourceNotFoundException("Order with id "+ orderId +" not found!");
		}

		userFacade.addOrder(id, orderId);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userId}/removeOrder/{orderId}")
	public void removeOrdertoUser(@PathParam("userId") Long id, @PathParam("orderId") Long orderId) {
		log.debug("Rest remove order with id ({}) from user with id ({})", id, orderId);

		if (userFacade.findById(id) == null) {
			throw new ResourceNotFoundException("User with id "+ id +" not found!");
		}
		if (orderFacade.findById(orderId) == null) {
			throw new ResourceNotFoundException("Order with id "+ orderId +" not found!");
		}

		userFacade.removeOrder(id, orderId);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/setSuperadmin")
	public void setSuperadmin(@QueryParam("id")Long id) {
		log.debug("Rest set UserRole.SUPERADMIN for user with id ({})", id);


		try {
			userFacade.setSuperadmin(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("User not found");
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/setSubmitter")
	public void setSubmitter(@QueryParam("id") Long id) {
		log.debug("Rest set UserRole.SUBMITTER for user with id ({})", id);

		try {
			userFacade.setSubmitter(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("User not found");
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/setPractitioner")
	public void setPractitioner(@QueryParam("id") Long id) {
		log.debug("Rest set UserRole.PRACTITIONER for user with id ({})", id);

		try {
			userFacade.setPractitioner(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException("User not found");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getBasket")
	public OrderDTO getBasket() {
		log.debug("Rest getBasket");

		//TODO : REMOVE THIS WHEN SECURITY IS IMPLEMENTED
		UserDTO fakeUser = userFacade.findByEmail("v.sassmann@gmail.com");
		if (fakeUser == null) {
			UserCreateDTO fakeUserCreate = new UserCreateDTO();
			fakeUserCreate.setEmail("v.sassmann@gmail.com");
			fakeUserCreate.setName("fake");
			fakeUserCreate.setSurname("user");
			fakeUserCreate.setRole(UserRole.SUPERADMIN);
			fakeUserCreate.setPassword("fakeuser");

			userFacade.create(fakeUserCreate);
			fakeUser = userFacade.findByEmail("v.sassmann@gmail.com");
		}
		return userFacade.getBasket(fakeUser.getId());
	}
}
