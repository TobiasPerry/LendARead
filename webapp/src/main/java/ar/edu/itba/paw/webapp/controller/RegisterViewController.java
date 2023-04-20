package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterViewController {
    private final String viewName = "views/registerView";

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView login(@Valid @ModelAttribute final RegisterForm registerForm) {
        return new ModelAndView(viewName);
    }

}

