package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.form.ChangePasswordForm;
import ar.edu.itba.paw.webapp.form.EmailForm;
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
public class ForgotPasswordController {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddAssetViewController.class);

    @Autowired
    public ForgotPasswordController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/forgotPassword")
    public ModelAndView forgotPassword(@ModelAttribute("emailForm") final EmailForm emailForm) {
        return new ModelAndView("/views/forgotPassword");
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ModelAndView forgotPasswordPost(@Valid @ModelAttribute("emailForm") final EmailForm emailForm, final BindingResult errors) {
        if (errors.hasErrors()) {
            LOGGER.warn("Forgot password form erros");
            return forgotPassword(emailForm);
        }
        userService.createChangePasswordToken(emailForm.getEmail());
        LOGGER.debug("The token for forgot password has been created");
        return new ModelAndView("redirect:/changePassword");
    }

    @RequestMapping("/changePassword")
    public ModelAndView changePassword(@ModelAttribute("changePasswordForm") final ChangePasswordForm changePasswordForm) {
        return new ModelAndView("/views/changePassword");
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ModelAndView changePasswordPost(@Valid @ModelAttribute("changePasswordForm") final ChangePasswordForm changePasswordForm, final BindingResult errors) {
        if (errors.hasErrors()) {
            return changePassword(changePasswordForm);
        }
        userService.changePassword(changePasswordForm.getToken(), changePasswordForm.getPassword());
        LOGGER.debug("The password has been changed");
        return new ModelAndView("redirect:/login");
    }
}
