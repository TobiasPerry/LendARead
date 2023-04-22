package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserHomeViewController {

    private static final String registerViewName = "views/userHomeView";

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView(registerViewName);
    }
}
