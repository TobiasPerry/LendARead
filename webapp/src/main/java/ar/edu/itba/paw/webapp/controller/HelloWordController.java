package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

@Controller
public class HelloWordController {
    private final ViewResolver viewResolver;


    @Autowired
    public HelloWordController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    //En el requestMapping podemos ir agregandole cosas para poder tener mas especificaciones
    @RequestMapping( "/")
    public ModelAndView helloWord(){
        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("/views/index");
        return mav;
    }

    @RequestMapping("/discover")
    public ModelAndView discoverVIew(){
        final ModelAndView mav = new ModelAndView("/views/discoverView");
        return mav;
    }

    @RequestMapping( "/lendView")
    public ModelAndView lendView(){
        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("/views/lendView");
        return mav;
    }

}
