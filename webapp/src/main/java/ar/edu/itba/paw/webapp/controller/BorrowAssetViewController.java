package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import ar.edu.itba.paw.webapp.presentation.FormServiceBorrowAssetView;
import ar.edu.itba.paw.webapp.presentation.FormValidationService;
import ar.edu.itba.paw.webapp.presentation.SnackbarService;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class BorrowAssetViewController {
    AssetAvailabilityService assetAvailabilityService;
    final String viewName = "views/borrowAssetView";

    private final FormServiceBorrowAssetView formElements = new FormServiceBorrowAssetView();

    @RequestMapping(value = "/borrowAsset", method = RequestMethod.POST)
    public ModelAndView borrowAsset(
            @Valid @ModelAttribute final BorrowAssetForm borrowAssetForm,
            final BindingResult errors, Model model, @RequestParam int id
    ){

        if(errors.hasErrors())
            return borrowAssetView(id, borrowAssetForm);

        System.out.println(borrowAssetForm);

        boolean borrowRequestSuccessful = assetAvailabilityService.borrowAsset();

        return borrowAssetView(id, borrowAssetForm);
    }

    @Autowired
    public BorrowAssetViewController(AssetAvailabilityService assetAvailabilityService){
       this.assetAvailabilityService = assetAvailabilityService;
    }

    @RequestMapping( "/borrowAssetView")
    public ModelAndView borrowAssetView(@RequestParam int id, @ModelAttribute("borrowAssetForm") final BorrowAssetForm borrowAssetForm){

        return new ModelAndView(viewName);
    }
}
