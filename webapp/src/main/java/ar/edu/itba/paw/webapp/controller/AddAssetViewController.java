package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.presentation.FormService;
import ar.edu.itba.paw.webapp.presentation.FormServiceAddAssetView;
import ar.edu.itba.paw.webapp.presentation.FormValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AddAssetViewController {
    private final ViewResolver viewResolver;
    FormService formElements = new FormServiceAddAssetView();
    final String viewName = "views/addAssetView";

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public String addAsset(
            Model model,
            HttpServletRequest request
    ){
        FormValidationService formValidationService = formElements.validateRequest(request);

        if(formValidationService.isValid())
            model.addAttribute("showSnackbarSucess", true);
        else {
            model.addAttribute("showSnackbarInvalid", true);
            StringBuilder message = new StringBuilder("Los siguientes argumentos estan mal: ");
            for (String invalidElementInfo : formValidationService) {
                message.append(invalidElementInfo);
                message.append('\n');
            }
            model.addAttribute("snackBarInvalidText", message.toString());
        }
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
