package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NotFoundController {

    @RequestMapping(value = "/**")
    ModelAndView notFoundView() {
        return new ModelAndView("/views/notFoundView");
    }
}
