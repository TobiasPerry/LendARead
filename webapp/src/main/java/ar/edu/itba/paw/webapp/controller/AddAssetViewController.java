package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.presentation.FormServiceAddAssetView;
import ar.edu.itba.paw.webapp.presentation.FormValidationService;
import ar.edu.itba.paw.webapp.presentation.SnackbarService;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.nio.file.Files;
import java.io.File;

import javax.validation.Valid;

@Controller
final public class AddAssetViewController {
    private final FormServiceAddAssetView formService;

    private final AssetExistanceService assetExistanceService;
    private final String viewName = "views/addAssetView";

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public ModelAndView addAsset(@RequestParam(required = false,name ="file") MultipartFile image,
                           @Valid @ModelAttribute final AddAssetForm addAssetForm,
                                 final BindingResult errors,
                                 Model model) {

        if(errors.hasErrors())
            return lendView(addAssetForm);

        boolean addedBookSuccessfully = assetExistanceService.addAssetInstance(formService.createAssetInstance(addAssetForm), handleImage(image));

        if(addedBookSuccessfully)
            SnackbarService.displaySuccess(model);

        return lendView(addAssetForm);
    }

    private static byte[] handleImage(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                return file.getBytes();
            } catch (Exception e) {
                //
            }
        }
        return null;
    }

    @Autowired
    public AddAssetViewController(AssetExistanceService assetExistanceService, FormServiceAddAssetView formServiceAddAssetView){
        this.assetExistanceService = assetExistanceService;
        this.formService = formServiceAddAssetView;
    }

    @RequestMapping( value = "/addAssetView",  method = RequestMethod.GET)
    public ModelAndView lendView(@ModelAttribute("addAssetForm") final AddAssetForm addAssetForm){
        final ModelAndView mav = new ModelAndView(viewName);

        mav.addObject("path","addAsset");

        return  mav;
    }
}
