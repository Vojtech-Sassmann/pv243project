package cz.fi.muni.TACOS.service.Impl;

import cz.fi.muni.TACOS.persistence.dao.UserDao;
import cz.fi.muni.TACOS.persistence.entity.Order;
import cz.fi.muni.TACOS.persistence.entity.User;
import cz.fi.muni.TACOS.persistence.enums.UserRole;
import cz.fi.muni.TACOS.service.AbstractEntityService;
import cz.fi.muni.TACOS.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;


/**
 * EntityService layer for Order Entity
 *
 * @author Pavel Vyskocil <vyskocilpavel@muni.cz>
 */
@ApplicationScoped
public class UserServiceImpl extends AbstractEntityService<User> implements UserService {


    private final UserDao userDao;

    @Inject
    public UserServiceImpl(UserDao userDao) {
        super(userDao);

        this.userDao = userDao;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<User> getAllForRole(UserRole role){
        return userDao.getAllForRole(role);
    }

    @Override
    public void addOrderAsSubmittedOrder(User user, Order order) {
        if (user.getOrders().contains(order)) {
            throw new IllegalArgumentException("Order is already in submittedOrders for user");
        }
        user.addSubmittedOrder(order);
    }

    @Override
    public void removeOrderFromSubmittedOrders(User user, Order order) {
        if (!user.getOrders().contains(order)) {
            throw new IllegalArgumentException("Order is not in submittedOrders for user");
        }
        user.removeSubmittedOrder(order);
    }
}
