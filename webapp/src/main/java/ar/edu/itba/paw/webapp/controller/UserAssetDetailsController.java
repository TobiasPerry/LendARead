package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.LendingCompletionUnsuccessfulException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.webapp.form.AssetInstanceForm;
import ar.edu.itba.paw.webapp.miscellaneous.CustomMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;


@Controller
final public class UserAssetDetailsController {

    private final AssetInstanceService assetInstanceService;
    private final AssetAvailabilityService assetAvailabilityService;

    private final UserAssetInstanceService userAssetInstanceService;

    private final UserService userService;

    private final LocationsService locationsService;

    private final static String VIEW_NAME = "views/userHomeAssetDetail/userBookDetails";
    private final static String VIEW_NAME_ASSET_EDIT = "views/editAssetInstance";

    private final static String VIEW_NAME_LENDING_VIEW = "views/userHomeAssetDetail/lendingBookDetails";

    private final static String TABLE = "table", ASSET = "asset", NAV_BAR_PATH = "userHome", LENDING = "lending", ASSET_INSTANCE = "assetInstance", LOCATIONS = "locations";

    private final static String BACKURL = "backUrl";
    private final static String LENDED_BOOKS = "lended_books", MY_BOOKS = "my_books", BORROWED_BOOKS = "borrowed_books";


    @Autowired
    public UserAssetDetailsController(final AssetInstanceService assetInstanceService, final AssetAvailabilityService assetAvailabilityService, final UserAssetInstanceService userAssetInstanceService, final UserService userService, final LocationsService locationsService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
        this.locationsService = locationsService;
    }

    @RequestMapping(value = "/userHomeReturn", method = RequestMethod.GET)
    public ModelAndView returnUserHome() {
        return new ModelAndView("redirect:/userHome");
    }

    @RequestMapping(value = "/lentBookDetails/{lendingId}", method = RequestMethod.GET)
    public ModelAndView lentBookDetail(@PathVariable final int lendingId) throws AssetInstanceNotFoundException {
        LendingImpl lending = userAssetInstanceService.getBorrowedAssetInstance(lendingId);

        return new ModelAndView(VIEW_NAME_LENDING_VIEW)
                .addObject(LENDING, userAssetInstanceService.getBorrowedAssetInstance(lendingId))
                .addObject(TABLE, LENDED_BOOKS);
    }

    @RequestMapping(value = "/myBookDetails/{id}", method = RequestMethod.GET)
    public ModelAndView myBookDetails(HttpServletRequest request, @PathVariable final int id) throws AssetInstanceNotFoundException {
        return new ModelAndView(VIEW_NAME)
                .addObject(ASSET, assetInstanceService.getAssetInstance(id))
                .addObject(TABLE, MY_BOOKS).addObject("lendings",assetAvailabilityService.getActiveLendings(assetInstanceService.getAssetInstance(id)));
    }

    @RequestMapping(value = "/borrowedBookDetails/{lendingId}", method = RequestMethod.GET)
    public ModelAndView borrowedBookDetails(@PathVariable final int lendingId) throws AssetInstanceNotFoundException {
        return new ModelAndView(VIEW_NAME_LENDING_VIEW)
                .addObject(LENDING, userAssetInstanceService.getBorrowedAssetInstance(lendingId))
                .addObject(TABLE, BORROWED_BOOKS);
    }

    @RequestMapping(value = "/deleteAsset/{id}", method = RequestMethod.POST)
    public ModelAndView deleteAsset(@PathVariable("id") final int id) throws AssetInstanceNotFoundException {
        assetInstanceService.removeAssetInstance(id);
        return new ModelAndView("redirect:/userHome");
    }

    @RequestMapping(value = "/returnAsset/{lendingId}", method = RequestMethod.POST)
    public ModelAndView returnAsset(@PathVariable("lendingId") final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        assetAvailabilityService.returnAsset(lendingId);
        return new ModelAndView("redirect:/userHome");
    }

    @RequestMapping(value = "/confirmAsset/{lendingId}", method = RequestMethod.POST)
    public ModelAndView confirmAsset(@PathVariable("lendingId") final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        assetAvailabilityService.confirmAsset(lendingId);
        return new ModelAndView("redirect:/lentBookDetails/" + lendingId);
    }

    @RequestMapping(value = "/rejectAsset/{lendingId}", method = RequestMethod.POST)
    public ModelAndView rejectAsset(@PathVariable("lendingId") final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        assetAvailabilityService.rejectAsset(lendingId);
        return new ModelAndView("redirect:/lentBookDetails/" + lendingId);
    }

    @RequestMapping(value = "/cancelAsset/{lendingId}", method = RequestMethod.POST)
    public ModelAndView cancelAsset(@PathVariable("lendingId") final int lendingId) throws AssetInstanceNotFoundException, LendingCompletionUnsuccessfulException {
        assetAvailabilityService.cancelAsset(lendingId);
        return new ModelAndView("redirect:/borrowedBookDetails/" + lendingId);
    }

    @RequestMapping(value = "/changeReservability/{id}", method = RequestMethod.POST)
    public ModelAndView changeReservability(@PathVariable("id") final int id) throws AssetInstanceNotFoundException {
        assetAvailabilityService.changeReservability(id);
        return new ModelAndView("redirect:/myBookDetails/" + id);
    }

    @RequestMapping(value = "/changeStatus/{id}", method = RequestMethod.POST)
    public ModelAndView changeMyBookStatus(@PathVariable("id") final int id) throws AssetInstanceNotFoundException {
        AssetInstanceImpl assetInstance = assetInstanceService.getAssetInstance(id);

        if (assetInstance.getAssetState().isPublic())
            assetAvailabilityService.setAssetPrivate(id);
        else if (assetInstance.getAssetState().isPrivate())
            assetAvailabilityService.setAssetPublic(id);

        return new ModelAndView("redirect:/myBookDetails/" + id);
    }

    @RequestMapping(value = "/editAsset/{id}", method = RequestMethod.POST)
    public ModelAndView changeAsset(@PathVariable("id") final int id, @Valid @ModelAttribute AssetInstanceForm assetInstanceForm, final BindingResult errors) throws AssetInstanceNotFoundException, IOException, UserNotFoundException {
        if (errors.hasErrors()) {
            return changeAsset(id, assetInstanceForm);
        }
        LocationImpl location = locationsService.getLocation(assetInstanceForm.getId());
        assetInstanceService.changeAssetInstance(id, PhysicalCondition.fromString(assetInstanceForm.getPhysicalCondition()), assetInstanceForm.getMaxDays(), location, assetInstanceForm.getImage().getBytes(), assetInstanceForm.getDescription());
        return new ModelAndView("redirect:/myBookDetails/" + id);
    }

    @RequestMapping(value = "/editAsset/{id}", method = RequestMethod.GET)
    public ModelAndView changeAsset(@PathVariable("id") final int id, @ModelAttribute("assetInstanceForm") final AssetInstanceForm assetInstanceForm) throws AssetInstanceNotFoundException, UserNotFoundException {
        AssetInstanceImpl assetInstance = assetInstanceService.getAssetInstance(id);
        CustomMultipartFile file = new CustomMultipartFile(assetInstance.getImage().getPhoto());

        return new ModelAndView(VIEW_NAME_ASSET_EDIT)
                .addObject(ASSET_INSTANCE, assetInstance).addObject(LOCATIONS, locationsService.getLocations(userService.getUser(userService.getCurrentUser())));
    }

    @ModelAttribute
    public void addAttributes(final Model model) {
        model.addAttribute("path", NAV_BAR_PATH);
    }

}
