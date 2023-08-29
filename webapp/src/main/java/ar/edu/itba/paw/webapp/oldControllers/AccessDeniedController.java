package ar.edu.itba.paw.webapp.oldControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessDeniedController {
    @RequestMapping("/errors/403")
    public ModelAndView error403(){
        return new ModelAndView("/views/403View");
    }
}
