package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.FormServiceAddAssetView;
import ar.edu.itba.paw.webapp.form.SnackbarService;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
final public class AddAssetViewController {
    private final FormServiceAddAssetView formService;

    private final AssetExistanceService assetExistanceService;
    private final String viewName = "views/addAssetView";

    private final String SUCESS_MSG = "Libro agregado exitosamente!";
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("showSnackbarInvalid", true);
        redirectAttributes.addFlashAttribute("snackBarInvalidTextTitle", "File size exceeds limit");
        return "redirect:/addAssetView";
    }

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public ModelAndView addAsset(@RequestParam(name ="file") MultipartFile image,
                                 @Valid @ModelAttribute final AddAssetForm addAssetForm,
                                 final BindingResult errors,
                                 Model model) {


        if(errors.hasErrors() || image.isEmpty())
            return addAssetView(addAssetForm)
                    .addObject("showSnackbarInvalid", true)
                    .addObject("snackBarInvalidTextTitle",  image.isEmpty() ? "Falta la imagen \n" : "");

        boolean addedBookSuccessfully = assetExistanceService.addAssetInstance(formService.createAssetInstance(addAssetForm), handleImage(image));

        if(addedBookSuccessfully) {
            SnackbarService.displaySuccess(model, SUCESS_MSG);
            return addAssetView(addAssetForm);
        }

        return  addAssetView(addAssetForm)
                    .addObject("showSnackbarInvalid", true)
                    .addObject("snackBarInvalidTextTitle",  "Hubo un error guardando el libro");
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
    public ModelAndView addAssetView(@ModelAttribute("addAssetForm") final AddAssetForm addAssetForm){
        final ModelAndView mav = new ModelAndView(viewName);

        mav.addObject("path","addAsset");
        return  mav;
    }
}
