package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import java.lang.reflect.Method;

@Controller
public class AssetInfoViewController {

    ViewResolver viewResolver;

    @Autowired
    public AssetInfoViewController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView assetInfoView() {
        final ModelAndView mvc = new ModelAndView("/views/assetInfoView");
        return mvc;
    }
}
