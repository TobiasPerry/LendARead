package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.presentation.FormServiceAddAssetView;
import ar.edu.itba.paw.webapp.presentation.FormValidationService;
import ar.edu.itba.paw.webapp.presentation.SnackbarService;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
final public class AddAssetViewController {
    private final FormServiceAddAssetView formService;

    private final AssetExistanceService assetExistanceService;
    private final String viewName = "views/addAssetView";

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public String addAsset(@RequestParam("file") MultipartFile file,
            Model model,
            HttpServletRequest request
    ){
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select an image to upload.");
            return "views/addAssetView";
        }

        byte[] fileByteArray = new byte[0];

        try {
            fileByteArray = file.getBytes();
        } catch (Exception e) {
           //
        }

        FormValidationService formValidationService = formService.validateRequest(request);

        SnackbarService.displayValidation(model, formValidationService);

        if(!formValidationService.isValid())
            return viewName;

        boolean addedBookSuccessfully = assetExistanceService.addAssetInstance(formService.createAssetInstance(request), fileByteArray);
        if(addedBookSuccessfully)
            SnackbarService.displaySuccess(model);

        return viewName;
    }
    @Autowired
    public AddAssetViewController(AssetExistanceService assetExistanceService, FormServiceAddAssetView formServiceAddAssetView){
        this.assetExistanceService = assetExistanceService;
        this.formService = formServiceAddAssetView;
    }

    @RequestMapping( "/addAssetView")
    public ModelAndView lendView(){
        final ModelAndView mav = new ModelAndView(viewName);

        mav.addObject("path","addAsset");

        return  mav;
    }
}
