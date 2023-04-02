package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.presentation.FormElements;
import ar.edu.itba.paw.webapp.presentation.FormElementsAddAsset;
import ar.edu.itba.paw.webapp.presentation.FormValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AddAssetViewController {
    private final ViewResolver viewResolver;
    FormElements formElements = new FormElementsAddAsset();
    final String viewName = "views/addAssetView";

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public String addAsset(
            Model model,
            HttpServletRequest request
    ){
        FormValidation formValidation = formElements.validateRequest(request);

        if(formValidation.isValid())
            model.addAttribute("showSnackbarSucess", true);
        else
            model.addAttribute("showSnackbarInvalid", true);

        return viewName;
    }

    @Autowired
    public AddAssetViewController(@Qualifier("viewResolver")final ViewResolver vr){
        this.viewResolver = vr;
    }

    @RequestMapping( "/addAssetView")
    public ModelAndView lendView(){
        return new ModelAndView(viewName);
    }
}
