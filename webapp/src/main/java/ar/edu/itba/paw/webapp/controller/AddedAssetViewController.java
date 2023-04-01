package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

@Controller
public class AddedAssetViewController {
    private final ViewResolver viewResolver;

    @Autowired
    public AddedAssetViewController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    @RequestMapping( "/addedAssetView")
    public ModelAndView lendView(){
        final ModelAndView mav = new ModelAndView("/views/addedAssetView");
        return mav;
    }
}
