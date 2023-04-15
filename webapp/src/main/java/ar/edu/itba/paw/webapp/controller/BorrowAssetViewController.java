package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ImageService;
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
import java.util.Base64;

@Controller
final public class BorrowAssetViewController {
    private AssetAvailabilityService assetAvailabilityService;
    private final String viewName = "views/borrowAssetView";
    private final static String SUCCESS_MSG = "Libro pedido exitosamente!";

    private ImageService imageService;

    @RequestMapping(value = "/borrowAsset", method = RequestMethod.POST)
    public ModelAndView borrowAsset( @Valid @ModelAttribute final BorrowAssetForm borrowAssetForm,
                                    final BindingResult errors, Model model,
                                    @RequestParam("id") int id){

        if(errors.hasErrors())
            return borrowAssetView(borrowAssetForm, id).addObject("showSnackbarInvalid", true);

        boolean borrowRequestSuccessful = assetAvailabilityService.borrowAsset();

        if(borrowRequestSuccessful)
            SnackbarService.displaySuccess(model, SUCCESS_MSG);

        return borrowAssetView(new BorrowAssetForm(), id);
    }

    @Autowired
    public BorrowAssetViewController(AssetAvailabilityService assetAvailabilityService, ImageService imageService){
       this.assetAvailabilityService = assetAvailabilityService;
       this.imageService = imageService;
    }

    @RequestMapping( value = "/borrowAssetView", method = RequestMethod.GET)
    public ModelAndView borrowAssetView(@ModelAttribute("borrowAssetForm") final BorrowAssetForm borrowAssetForm, @RequestParam("id") int id){
        String image = Base64.getEncoder().encodeToString(imageService.getPhoto(id));

        return new ModelAndView(viewName).addObject("id", id).addObject("bookImage", image);
    }
}
