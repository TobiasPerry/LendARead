package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.Language;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.SnackbarControl;
import ar.edu.itba.paw.webapp.miscellaneous.FormFactoryAddAssetView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@Controller
final public class AddAssetViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddAssetViewController.class);


    private final UserService userService;
    private final AssetExistanceService assetExistanceService;
    private final LanguagesService languagesService;
    private final static String viewName = "views/addAssetView";
    private final static String NAV_BAR_PATH = "addAsset", INVALID_SNACKBAR = "showSnackbarInvalid";

    @Autowired
    public AddAssetViewController(UserService userService, AssetExistanceService assetExistanceService, LanguagesService languagesService) {
        this.userService = userService;
        this.assetExistanceService = assetExistanceService;
        this.languagesService = languagesService;
    }

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public ModelAndView addAsset(@RequestParam(name = "file") final MultipartFile image,
                                 @Valid @ModelAttribute final AddAssetForm addAssetForm,
                                 final BindingResult errors, HttpServletResponse response) throws InternalErrorException {

        byte[] parsedImage = FormFactoryAddAssetView.getByteArray(image);

        if (errors.hasErrors() || parsedImage == null)
            return addAssetView(addAssetForm, false, -1, true).addObject(INVALID_SNACKBAR, true);

        try {
            LOGGER.info("AssetInstance has ben created");
            AssetInstanceImpl assetInstance = assetExistanceService.addAssetInstance(FormFactoryAddAssetView.createAssetInstance(addAssetForm, userService.getCurrentUser()), parsedImage);
            return new ModelAndView("redirect:/addAssetView?succes=true&&id=" + assetInstance.getId());
        } catch (InternalErrorException e) {
            LOGGER.warn("Could not create assetInstance");
            return addAssetView(addAssetForm, false, -1, false).addObject(INVALID_SNACKBAR, true);
        }
    }

    @RequestMapping(value = "/addAssetView", method = RequestMethod.GET)
    public ModelAndView addAssetView(@ModelAttribute("addAssetForm") final AddAssetForm addAssetForm, @RequestParam(required = false, name = "succes") boolean success, @RequestParam(required = false, name = "id") Integer id, @RequestParam(required = false, name = "invalidImg") boolean invalidImg) {
        ModelAndView mav = new ModelAndView(viewName).addObject("borrowerUser", String.valueOf(userService.getCurrentUserIsBorrower()));
        List<LanguageImpl> languages = languagesService.getLanguages();
        mav.addObject("langs", languages);
        if (id != null)
            mav.addObject("assetId", id);
        if (success)
            SnackbarControl.displaySuccess(mav);
        if (invalidImg) {
            mav.addObject("invalidImg", true);
        }
        return mav;
    }


    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", NAV_BAR_PATH);
    }
}
