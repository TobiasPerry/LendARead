package ar.edu.itba.paw.webapp.controller;

import interfaces.UserService;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

@Controller
public class HelloWordController {
    private final ViewResolver viewResolver;

    @Autowired
    @Qualifier("userServiceImp")
    private UserService us;

    @Autowired
    public HelloWordController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    //En el requestMapping podemos ir agregandole cosas para poder tener mas especificaciones
    @RequestMapping( "/")
    public ModelAndView helloWord(){

        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("helloword/index");
        // Esto es una gran vulnerabilidad ya que el codigo que pongamos ahi lo va a renderear directamente
        mav.addObject("username","<span style='color:red'>PAW</span>");
        return mav;
    }
    @RequestMapping("/user/{userId}")
    public ModelAndView regiter(@PathVariable("userId") String id){
        User user = us.searchById(Integer.parseInt(id));
        final ModelAndView mav = new ModelAndView("helloword/user");
        mav.addObject("userName",user.getName());
        return mav;
    }
}
