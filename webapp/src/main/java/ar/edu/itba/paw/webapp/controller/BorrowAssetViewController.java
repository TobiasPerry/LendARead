package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.ImageService;
import ar.edu.itba.paw.models.userContext.factories.UserFactory;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import ar.edu.itba.paw.webapp.form.SnackbarControl;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Base64;

@Controller
final public class BorrowAssetViewController {
    private final AssetAvailabilityService assetAvailabilityService;

    private final static String DEFAULT_STRING = "";

    private final static int DEFAULT_INT = -1;
    private final static String viewName = "views/borrowAssetView";
    private final static String SUCCESS_MSG = "Libro pedido exitosamente!";

    private final ImageService imageService;

    @RequestMapping(value = "/borrowAsset", method = RequestMethod.POST)
    public ModelAndView borrowAsset( @Valid @ModelAttribute final BorrowAssetForm borrowAssetForm,
                                    final BindingResult errors, Model model,
                                    @RequestParam("id") int id,@RequestParam("imageId") int imageId){

        if(errors.hasErrors())
            return borrowAssetView(borrowAssetForm,id,imageId).addObject("showSnackbarInvalid", true);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        LocalDate devolutionDay = LocalDate.now().plusWeeks(2);
        boolean borrowRequestSuccessful = assetAvailabilityService.borrowAsset(id, UserFactory.createUser(DEFAULT_INT,email,DEFAULT_STRING,DEFAULT_STRING,DEFAULT_STRING),devolutionDay);

        if(borrowRequestSuccessful) {
            ModelAndView borrowAssetView =  borrowAssetView(borrowAssetForm,id,imageId);
            SnackbarControl.displaySuccess(borrowAssetView,SUCCESS_MSG);
            return borrowAssetView;
        }

       return borrowAssetView(borrowAssetForm,id,imageId).addObject("showSnackbarInvalid", true);
    }

    @Autowired
    public BorrowAssetViewController(AssetAvailabilityService assetAvailabilityService, ImageService imageService){
       this.assetAvailabilityService = assetAvailabilityService;
       this.imageService = imageService;
    }

    @RequestMapping( value = "/borrowAssetView", method = RequestMethod.GET)
    public ModelAndView borrowAssetView(@ModelAttribute("borrowAssetForm") final BorrowAssetForm borrowAssetForm, @RequestParam("id") int id,@RequestParam("imageId") int imageId){
        String image = Base64.getEncoder().encodeToString(imageService.getPhoto(imageId));

        return new ModelAndView(viewName).addObject("id", id).addObject("bookImage", image).addObject("imageId",imageId);
    }
}
