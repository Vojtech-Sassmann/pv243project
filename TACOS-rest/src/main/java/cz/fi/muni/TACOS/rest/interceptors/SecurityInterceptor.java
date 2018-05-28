package cz.fi.muni.TACOS.rest.interceptors;

import cz.fi.muni.TACOS.dto.UserDTO;
import cz.fi.muni.TACOS.facade.UserFacade;
import cz.fi.muni.TACOS.rest.utils.SecurityUtils;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Arrays;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Interceptor
@Secured
@Priority(Interceptor.Priority.APPLICATION)
public class SecurityInterceptor {

    @Inject
    private HttpServletRequest request;

    @Inject
    private UserFacade userFacade;

    @AroundInvoke
    Object intercept(InvocationContext ctx) throws Exception {


        Cookie[] cookies = request.getCookies();
        System.out.println(request.getMethod());
        if (cookies == null) {
            throw new NotAuthorizedException("Not logged in.");
        }
        System.out.println("COOKIES: " + cookies.length);
        for (Cookie c : cookies) {
            System.out.println("c : " + c.getName() + "; " + c.getValue());
        }

        String token = null;

        for (Cookie cookie : cookies) {
            if (SecurityUtils.COOKIE.equals(cookie.getName())) {
                token = SecurityUtils.decrypt(SecurityUtils.KEY, SecurityUtils.INIT_VECTOR, cookie.getValue());
            }
        }

        if (token == null) {
            throw new NotAuthorizedException("Not logged in.");
        }

        String[] data = token.split(";", 2);
        if (data.length != 2) {
            throw new NotAuthorizedException("Not logged in.");
        }

        long id;
        String email;

        try {
            id = Long.parseLong(data[0]);
        } catch (NumberFormatException e) {
            throw new NotAuthorizedException("Not logged in.");
        }
        email = data[1];

        UserDTO user = userFacade.findById(id);
        if (!user.getEmail().equals(email)) {
            throw new NotAuthorizedException("Not logged in.");
        }

        Secured annotation = ctx.getMethod().getAnnotation(Secured.class);
        if (annotation != null && !Arrays.asList(annotation.roles()).contains(user.getRole())) {
            throw new ForbiddenException("Insufficient permission.");
        }

        ctx.getContextData().put(SecurityUtils.AUTH_USER, user);

        return ctx.proceed();
    }
}
