package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterViewController {
    private final static String registerViewName = "views/registerView";
    private final static String registerVerificationViewName = "views/registerVerifyView";

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@Valid @ModelAttribute final RegisterForm registerForm, final BindingResult errors) {

        //logic with service

        if(errors.hasErrors()) {
            return register(registerForm);
        }

        return registerVerification();
    }

    @RequestMapping(value = "/registerVerify", method = RequestMethod.GET)
    public ModelAndView registerVerification() {
        return new ModelAndView(registerVerificationViewName);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@Valid @ModelAttribute final RegisterForm registerForm) {
        return new ModelAndView(registerViewName);
    }

}

