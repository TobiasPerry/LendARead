package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginViewController {
    private final String viewName = "views/loginView";
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost(@Valid @ModelAttribute final LoginForm loginForm, final BindingResult errors) {

        //login service logic

        if(errors.hasErrors()) {
            return login(loginForm);
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@Valid @ModelAttribute final LoginForm loginForm) {
        return new ModelAndView(viewName);
    }

}
