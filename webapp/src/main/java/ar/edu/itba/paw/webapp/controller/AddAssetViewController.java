package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.InternalErrorException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetExistanceService;
import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.edu.itba.paw.interfaces.LocationsService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;
import ar.edu.itba.paw.models.userContext.implementations.Behaviour;
import ar.edu.itba.paw.models.userContext.implementations.UserImpl;
import ar.edu.itba.paw.webapp.form.AddAssetForm;
import ar.edu.itba.paw.webapp.form.LocationForm;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@Controller
final public class AddAssetViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddAssetViewController.class);


    private final UserService userService;
    private final AssetExistanceService assetExistanceService;
    private final LanguagesService languagesService;

    private final LocationsService locationsService;

    private final static String viewName = "views/addAssetView";
    private final static String NAV_BAR_PATH = "addAsset", INVALID_SNACKBAR = "showSnackbarInvalid";

    @Autowired
    public AddAssetViewController(UserService userService, AssetExistanceService assetExistanceService, LanguagesService languagesService, LocationsService locationsService) {
        this.userService = userService;
        this.assetExistanceService = assetExistanceService;
        this.languagesService = languagesService;
        this.locationsService = locationsService;
    }

    @RequestMapping(value = "/addAsset", method = RequestMethod.POST)
    public ModelAndView addAsset(@RequestParam(name = "file") final MultipartFile image,
                                 @Valid @ModelAttribute final AddAssetForm addAssetForm,
                                 final BindingResult errors,
                                 @ModelAttribute final LocationForm locationForm,
                                  HttpServletResponse response) throws UserNotFoundException {

        byte[] parsedImage = FormFactoryAddAssetView.getByteArray(image);

        if (errors.hasErrors() || parsedImage == null)
            return addAssetView(addAssetForm, false, -1, true,locationForm).addObject(INVALID_SNACKBAR, true).addObject("errorCode",1);

        UserImpl user = userService.getUser(userService.getCurrentUser());

        try {
            AssetInstanceImpl assetInstance = assetExistanceService.addAssetInstance(FormFactoryAddAssetView.createAssetInstance(addAssetForm, user, locationsService.getLocation(addAssetForm.getId())), parsedImage);
            LOGGER.info("AssetInstance has ben created");
            return new ModelAndView("redirect:/addAssetView?succes=true&&id=" + assetInstance.getId());
        } catch (InternalErrorException e) {
            LOGGER.warn("Could not create assetInstance");
            return addAssetView(addAssetForm, false, -1, false,locationForm).addObject(INVALID_SNACKBAR, true);
        }
    }

    @RequestMapping(value = "/addAssetView", method = RequestMethod.GET)
    public ModelAndView addAssetView(
            @ModelAttribute("addAssetForm") final AddAssetForm addAssetForm,
            @RequestParam(required = false, name = "succes") boolean success,
            @RequestParam(required = false, name = "id") Integer id,
            @RequestParam(required = false, name = "invalidImg") boolean invalidImg,
            @ModelAttribute("locationForm") final LocationForm locationForm
    ) throws UserNotFoundException {
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
        return mav.addObject("locations", locationsService.getLocations(userService.getUser(userService.getCurrentUser()))).addObject("errorCode",-1);
    }
    @RequestMapping(value = "/defaultLocation", method = RequestMethod.POST)
    public ModelAndView changeRole(final HttpServletRequest request, @Valid @ModelAttribute("locationForm") final LocationForm locationForm,final BindingResult errors,@ModelAttribute  AddAssetForm addAssetForm) throws UserNotFoundException {
        if(errors.hasErrors())
            return addAssetView( addAssetForm,false, -1, true,locationForm).addObject("errorCode",2);
        locationsService.addLocation(locationForm.getId(), locationForm.getName(), locationForm.getLocality(), locationForm.getProvince(), locationForm.getCountry(), locationForm.getZipcode(), userService.getUser(userService.getCurrentUser()));
        userService.changeRole(userService.getCurrentUser(), Behaviour.LENDER);
        return new ModelAndView("redirect:/addAssetView"  );
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", NAV_BAR_PATH);
    }
}
