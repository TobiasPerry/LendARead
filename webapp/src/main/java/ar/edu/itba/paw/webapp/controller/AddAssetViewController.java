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
    private final static String INVALID_INPUT_MESSAGE ="Los siguientes argumentos estan mal:" ;
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
        else
           updateSnackbar(model, formValidationService);

        return viewName;
    }

    private void updateSnackbar(Model model, FormValidationService formValidationService) {
        StringBuilder message = new StringBuilder();
        int num = 0;
        for (String invalidElementInfo : formValidationService) {
            message.append('\n');
            message.append(num++);
            message.append(". ");
            message.append(invalidElementInfo);
        }
        model.addAttribute("snackBarInvalidTextTitle", INVALID_INPUT_MESSAGE);
        model.addAttribute("snackBarInvalidText", message.toString());
        model.addAttribute("showSnackbarInvalid", true);
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
