package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import java.util.List;

@Controller
public class HelloWordController {
    private final ViewResolver viewResolver;
    AssetInstanceService assetInstanceService;

    @Autowired
    public HelloWordController(@Qualifier("viewResolver")final ViewResolver vr, AssetInstanceService assetInstanceService){
        this.viewResolver = vr;
        this.assetInstanceService = assetInstanceService;
    }

    //En el requestMapping podemos ir agregandole cosas para poder tener mas especificaciones
    @RequestMapping( "/")
    public ModelAndView helloWord(){
        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("/views/index");
        List<AssetInstance> books = assetInstanceService.getAllAssetsInstances();
        mav.addObject("books", books);
        mav.addObject("path","home");
        return mav;
    }

    @RequestMapping("/discover")
    public ModelAndView discoverVIew(){
        final ModelAndView mav = new ModelAndView("/views/discoverView");
        return mav;
    }

    @RequestMapping( "/assetView")
    public ModelAndView assetView(){
        //El objeto ModelAndView nos deja detener el modelo y la view
        final ModelAndView mav = new ModelAndView("/views/assetView");
        return mav;
    }
}
