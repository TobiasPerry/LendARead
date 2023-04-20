package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.AddAssetForm;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@Valid @ModelAttribute final AddAssetForm addAssetForm) {
        return new ModelAndView(viewName);
    }

}
