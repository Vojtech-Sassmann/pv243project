package cz.fi.muni.TACOS.facade.impl;

import cz.fi.muni.TACOS.dto.UserAuthenticateDTO;
import cz.fi.muni.TACOS.dto.UserCreateDTO;
import cz.fi.muni.TACOS.dto.UserDTO;
import cz.fi.muni.TACOS.facade.UserFacade;
import cz.fi.muni.TACOS.enums.UserRole;
import cz.fi.muni.TACOS.persistence.entity.User;
import cz.fi.muni.TACOS.service.BeanMappingService;
import cz.fi.muni.TACOS.service.OrderService;
import cz.fi.muni.TACOS.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of UserFacade Interface
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@Transactional(rollbackOn = { Exception.class })
@ApplicationScoped
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    private final OrderService orderService;

    private final BeanMappingService beanMappingService;

    @Inject
    public UserFacadeImpl(UserService userService, OrderService orderService, BeanMappingService beanMappingService) {
        this.userService = userService;
        this.orderService = orderService;
        this.beanMappingService = beanMappingService;
    }


    @Override
    public Long create(UserCreateDTO entity) {
        User user = beanMappingService.mapTo(entity, User.class);
        userService.createUser(user, entity.getPassword());
        return user.getId();
    }

    @Override
    public void delete(Long id) {
        userService.delete(userService.findById(id));

    }

    @Override
    public UserDTO findById(Long id) {
        User user = userService.findById(id);
        return beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {
        return beanMappingService.mapTo(userService.findByEmail(email), UserDTO.class);
    }

    @Override
    public List<UserDTO> getAll() {
        return beanMappingService.mapTo(userService.getAll(), UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllForRole(UserRole role) {
        return beanMappingService.mapTo(userService.getAllForRole(role), UserDTO.class);
    }

    @Override
    public void addOrder(Long userId, Long orderId) {
        userService.addOrderAsSubmittedOrder(userService.findById(userId), orderService.findById(userId));
    }

    @Override
    public void removeOrder(Long userId, Long orderId) {
        userService.removeOrderFromSubmittedOrders(userService.findById(userId), orderService.findById(orderId));

    }

    @Override
    public boolean authenticate(UserAuthenticateDTO user) {
        return userService.authenticate(
                userService.findByEmail(user.getEmail()), user.getPassword());
    }

    @Override
    public void setSuperadmin(Long id) {
        userService.setSuperadmin(userService.findById(id));
    }

    @Override
    public void setSubmitter(Long id) {
        userService.setSubmitter(userService.findById(id));
    }

    @Override
    public void setPractitioner(Long id) {
        userService.setPractitioner(userService.findById(id));
    }
}
