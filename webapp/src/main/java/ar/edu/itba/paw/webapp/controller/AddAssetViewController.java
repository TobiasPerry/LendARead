package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.presentation.FormService;
import ar.edu.itba.paw.webapp.presentation.FormServiceAddAssetView;
import ar.edu.itba.paw.webapp.presentation.FormValidationService;
import ar.edu.itba.paw.webapp.presentation.SnackbarService;
import interfaces.AssetInstanceService;
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
    private FormService formElements = new FormServiceAddAssetView();

    private final AssetInstanceService assetInstanceService;
    final String viewName = "views/addAssetView";

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public String addAsset(
            Model model,
            HttpServletRequest request
    ){
        FormValidationService formValidationService = formElements.validateRequest(request);

        SnackbarService.updateSnackbar(model, formValidationService);

        if(formValidationService.isValid()) {
            assetInstanceService.addAssetInstance();
        }
        return viewName;
    }
    @Autowired
    public AddAssetViewController(AssetInstanceService assetInstanceService){
        this.assetInstanceService = assetInstanceService;
    }

    @RequestMapping( "/addAssetView")
    public ModelAndView lendView(){
        final ModelAndView mav = new ModelAndView(viewName);

        mav.addObject("path","addAsset");

        return  mav;
    }
}
