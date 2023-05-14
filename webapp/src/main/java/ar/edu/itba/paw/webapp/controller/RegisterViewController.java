package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.models.userContext.interfaces.User;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;

@Controller
public class RegisterViewController {
    private final UserService userService;

    @Autowired
    public RegisterViewController(UserService userService) {
        this.userService = userService;
    }

    private final static String registerViewName = "views/registerView";
    private final static String registerVerificationViewName = "views/registerVerifyView";

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@Valid @ModelAttribute final RegisterForm registerForm, final BindingResult errors) {
        if(errors.hasErrors()) {
            return register(registerForm);
        }
        userService.createUser(registerForm.getEmail(),registerForm.getName(),"",registerForm.getPassword());
        userService.logInUser(registerForm.getEmail(),registerForm.getPassword());

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/registerVerify", method = RequestMethod.GET)
    public ModelAndView registerVerification() {
        return new ModelAndView(registerVerificationViewName);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register( @ModelAttribute("registerForm") final RegisterForm registerForm) {
        return new ModelAndView(registerViewName);
    }

}

