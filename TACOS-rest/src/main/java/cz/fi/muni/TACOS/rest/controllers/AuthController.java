package cz.fi.muni.TACOS.rest.controllers;

import cz.fi.muni.TACOS.dto.UserAuthenticateDTO;
import cz.fi.muni.TACOS.dto.UserDTO;
import cz.fi.muni.TACOS.facade.UserFacade;
import cz.fi.muni.TACOS.rest.ApiUris;
import cz.fi.muni.TACOS.rest.utils.SecurityUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@Path(ApiUris.URI_AUTH)
@Stateless
public class AuthController {

    @Inject
    private UserFacade userFacade;

    @Context
    private HttpServletResponse response;

    @Context
    private HttpServletRequest request;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean authorize(UserAuthenticateDTO authDTO) {

        UserDTO foundUser = userFacade.findByEmail(authDTO.getEmail());
        System.out.println("Found user: " + foundUser);

        if (foundUser == null) {
            return false;
        }

        try {
            if (userFacade.authenticate(authDTO)) {
                Cookie cookie = SecurityUtils.generateCookie(foundUser);
                response.addCookie(cookie);
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean logout() {

        Cookie[] cookies = request.getCookies();

        Cookie authCookie = null;

        if (cookies == null) {
            return false;
        }

        for (Cookie c : cookies) {
            if (SecurityUtils.COOKIE.equals(c.getName())) {
                authCookie = c;
            }
        }

        System.out.println("Found cookie: " + authCookie);
        if (authCookie == null) {
            return false;
        }

//        authCookie.setPath("/");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);
        return true;
    }
}
