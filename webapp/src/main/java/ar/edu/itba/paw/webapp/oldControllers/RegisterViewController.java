package ar.edu.itba.paw.webapp.oldControllers;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterViewController {
    private final UserService userService;

    @Autowired
    public RegisterViewController(UserService userService) {
        this.userService = userService;
    }

    private final static String registerViewName = "views/registerView";
    private static final Logger LOGGER = LoggerFactory.getLogger(AddAssetViewController.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(@Valid @ModelAttribute final RegisterForm registerForm, final BindingResult errors) {
        if (errors.hasErrors()) {
            LOGGER.warn("Errors in registers form");
            return register(registerForm);
        }
        userService.createUser(registerForm.getEmail(), registerForm.getName(), "", registerForm.getPassword());
        userService.logInUser(registerForm.getEmail(), registerForm.getPassword());
        LOGGER.debug("User successfully created");

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(@ModelAttribute("registerForm") final RegisterForm registerForm) {
        return new ModelAndView(registerViewName);
    }

}

