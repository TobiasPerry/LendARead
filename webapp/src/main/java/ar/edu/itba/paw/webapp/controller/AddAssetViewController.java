package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

@Controller
public class AddAssetViewController {
    private final ViewResolver viewResolver;

    @RequestMapping(value = "/addAssetView/addAsset", method = RequestMethod.POST)
    public ModelAndView addAsset(
            @RequestParam(value = "title", required = true) final String title,
            @RequestParam(value = "condition", required = true) final String condition
    ){
        // TODO Aca esta mal habria que volver o ir a una pagina de confirmacion no dejarlo en la misma pagina
        final ModelAndView mav = new ModelAndView("/views/addAssetView");
        System.out.println(title);
        System.out.println(condition);
        return mav;
    }

    @Autowired
    public AddAssetViewController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    @RequestMapping( "/addAssetView")
    public ModelAndView lendView(){
        final ModelAndView mav = new ModelAndView("/views/addAssetView");
        return mav;
    }
}
