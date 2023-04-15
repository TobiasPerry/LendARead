package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.interfaces.LendingsService;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import ar.edu.itba.paw.webapp.form.SnackbarService;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Date;

@Controller
public class BorrowAssetViewController {
    LendingsService lendingsService;
    final String viewName = "views/borrowAssetView";

    ImageService imageService;

    @RequestMapping(value = "/borrowAsset", method = RequestMethod.POST)
    public ModelAndView borrowAsset( @Valid @ModelAttribute final BorrowAssetForm borrowAssetForm,
                                    final BindingResult errors, Model model,
                                    @RequestParam("id") int id){

        if(errors.hasErrors())
            return borrowAssetView(borrowAssetForm, id);

        boolean borrowRequestSuccessful = lendingsService.lendBook(id,new UserImpl(-1,borrowAssetForm.getEmail(),borrowAssetForm.getName(),borrowAssetForm.getMessage()),new Date());

        if(borrowRequestSuccessful)
            SnackbarService.displaySuccess(model);

        return borrowAssetView(new BorrowAssetForm(), id);
    }

    @Autowired
    public BorrowAssetViewController(LendingsService lendingsService, ImageService imageService){
       this.lendingsService = lendingsService;
       this.imageService = imageService;
    }

    @RequestMapping( value = "/borrowAssetView", method = RequestMethod.GET)
    public ModelAndView borrowAssetView(@ModelAttribute("borrowAssetForm") final BorrowAssetForm borrowAssetForm, @RequestParam("id") int id){
        return new ModelAndView(viewName).addObject("id", id);
    }
}
