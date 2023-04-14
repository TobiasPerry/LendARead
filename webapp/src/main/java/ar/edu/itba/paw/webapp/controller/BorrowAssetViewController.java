package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.presentation.FormServiceBorrowAssetView;
import ar.edu.itba.paw.webapp.presentation.FormValidationService;
import ar.edu.itba.paw.webapp.presentation.SnackbarService;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BorrowAssetViewController {
    AssetAvailabilityService assetAvailabilityService;
    final String viewName = "views/borrowAssetView";

    private final FormServiceBorrowAssetView formElements = new FormServiceBorrowAssetView();

    @RequestMapping(value = "/borrowAsset", method = RequestMethod.POST)
    public String borrowAsset(
            Model model, HttpServletRequest request
    ){

        FormValidationService formValidationService = formElements.validateRequest(request);

        SnackbarService.displayValidation(model, formValidationService);

        if(formValidationService.isValid())
           assetAvailabilityService.borrowAsset();

        return viewName;
    }

    @Autowired
    public BorrowAssetViewController(AssetAvailabilityService assetAvailabilityService){
       this.assetAvailabilityService = assetAvailabilityService;
    }

    @RequestMapping( "/borrowAssetView")
    public ModelAndView lendView(@RequestParam int id){

        return new ModelAndView(viewName);
    }
}
