package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginLogOutViewController {
    private final String viewName = "views/loginView";


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView(viewName);
    }

}
