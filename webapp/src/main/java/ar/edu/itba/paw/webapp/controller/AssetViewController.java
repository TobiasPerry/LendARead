package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceBorrowException;
import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.DayOutOfRangeException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import ar.edu.itba.paw.webapp.form.SnackbarControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Controller
public class AssetViewController {
    private final static String SUCESS_MSG = "Libro agregado exitosamente!";

    private final AssetInstanceService assetInstanceService;
    private final AssetAvailabilityService assetAvailabilityService;


    @Autowired
    public AssetViewController(final AssetInstanceService assetInstanceService, final AssetAvailabilityService assetAvailabilityService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public ModelAndView assetInfoView(@PathVariable(name = "id") int id, @ModelAttribute("borrowAssetForm") final BorrowAssetForm borrowAssetForm,@RequestParam(required = false,name = "success") final boolean success) throws AssetInstanceNotFoundException{
        AssetInstance assetInstanceOpt = assetInstanceService.getAssetInstance(id);


        final ModelAndView mav = new ModelAndView("/views/assetView");
        if(success)
            SnackbarControl.displaySuccess(mav,SUCESS_MSG);
        mav.addObject("assetInstance", assetInstanceOpt);

        return mav;
    }

    @RequestMapping(value = "/requestAsset/{id}", method = RequestMethod.POST)
    public ModelAndView requestAsset(@PathVariable(name = "id") int id, @Valid @ModelAttribute final BorrowAssetForm borrowAssetForm,final BindingResult errors) throws AssetInstanceBorrowException, UserNotFoundException, AssetInstanceNotFoundException {
        if (errors.hasErrors()){
            return assetInfoView(id,borrowAssetForm,false);
        }
        //TODO PREGUNTAR
        if(borrowAssetForm.getDate() == null)
            System.out.println("ENTRE");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            assetAvailabilityService.borrowAsset(id, getCurrentUserEmail(), LocalDate.parse(borrowAssetForm.getDate(), formatter));
        }catch (DayOutOfRangeException ex){
            ModelAndView assetInfoView = assetInfoView(id,borrowAssetForm,false);

            assetInfoView.addObject("dayError",true);
            return assetInfoView;
        }

        return new ModelAndView(String.format("redirect:/info/%d?success=%s",id,"true"));
    }

    private String getCurrentUserEmail() {
        return ((UserDetails )SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

}
