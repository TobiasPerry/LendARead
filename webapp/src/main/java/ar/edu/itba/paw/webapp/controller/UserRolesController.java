package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserRolesController {

    private final UserService userService;

    @Autowired
    public UserRolesController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/changeRole", method = RequestMethod.POST)
    public ModelAndView changeRole(HttpServletRequest request) throws UserNotFoundException {

        userService.changeRole(userService.getCurrentUser(), Behaviour.LENDER);

        return new ModelAndView("redirect:" + request.getHeader("Referer"));
    }


}
