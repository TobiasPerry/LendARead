package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.LendingCompletionUnsuccessfulException;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.AssetInstanceImpl;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.PhysicalCondition;
import ar.edu.itba.paw.models.userContext.factories.LocationFactory;
import ar.edu.itba.paw.models.userContext.implementations.LocationImpl;
import ar.edu.itba.paw.webapp.form.AssetInstanceForm;
import org.apache.commons.io.FileUtils;
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
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


@Controller
final public class UserAssetDetailsController {

    private final AssetInstanceService assetInstanceService;
    private final AssetAvailabilityService assetAvailabilityService;

    private final UserAssetInstanceService userAssetInstanceService;

    private final static String VIEW_NAME = "views/userHomeAssetDetail/userBookDetails";
    private final static String VIEW_NAME_ASSET_EDIT = "views/editAssetInstance";

    private final static String VIEW_NAME_LENDING_VIEW = "views/userHomeAssetDetail/lendingBookDetails";

    private final static String TABLE = "table", ASSET = "asset", NAV_BAR_PATH = "userHome", LENDING = "lending",ASSET_INSTANCE = "assetInstance";

    private final static String BACKURL = "backUrl";
    private final static String LENDED_BOOKS = "lended_books", MY_BOOKS = "my_books", BORROWED_BOOKS = "borrowed_books";


    @Autowired
    public UserAssetDetailsController(final AssetInstanceService assetInstanceService, final AssetAvailabilityService assetAvailabilityService, UserAssetInstanceService userAssetInstanceService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
        this.userAssetInstanceService = userAssetInstanceService;
    }

    @RequestMapping(value = "/userHomeReturn", method = RequestMethod.GET)
    public ModelAndView returnUserHome() {
        return new ModelAndView("redirect:/userHome");
    }

    @RequestMapping(value = "/lentBookDetails/{lendingId}", method = RequestMethod.GET)
    public ModelAndView lentBookDetail(@PathVariable final int lendingId) throws AssetInstanceNotFoundException {
        return new ModelAndView(VIEW_NAME_LENDING_VIEW)
                .addObject(LENDING, userAssetInstanceService.getBorrowedAssetInstance(lendingId))
                .addObject(TABLE, LENDED_BOOKS);
    }

    @RequestMapping(value = "/myBookDetails/{id}", method = RequestMethod.GET)
    public ModelAndView myBookDetails(HttpServletRequest request, @PathVariable final int id) throws AssetInstanceNotFoundException {
        return new ModelAndView(VIEW_NAME)
                .addObject(ASSET, assetInstanceService.getAssetInstance(id))
                .addObject(TABLE, MY_BOOKS);
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
    public ModelAndView changeAsset(@PathVariable("id") final int id, @Valid @ModelAttribute AssetInstanceForm assetInstanceForm, final BindingResult errors ) throws AssetInstanceNotFoundException, IOException {
        if (errors.hasErrors()) {
            return new ModelAndView("redirect:/myBookDetails/" + id);
        }
        Optional<LocationImpl> location =Optional.of(LocationFactory.createLocation(assetInstanceForm.getZipcode(),assetInstanceForm.getLocality(),assetInstanceForm.getProvince(),assetInstanceForm.getCountry()));
        Optional<PhysicalCondition> physicalCondition;
        Optional<Integer> maxLendingDays;
        if (Objects.equals(assetInstanceForm.getPhysicalCondition(), ""))
            physicalCondition = Optional.empty();
        else
            physicalCondition = Optional.of(PhysicalCondition.fromString(assetInstanceForm.getPhysicalCondition()));
        if (assetInstanceForm.getMaxDays() < 0)
            maxLendingDays = Optional.empty();
        else
            maxLendingDays = Optional.of(assetInstanceForm.getMaxDays());
        assetInstanceService.changeAssetInstance(id,physicalCondition, maxLendingDays,location, Optional.of(assetInstanceForm.getImage().getBytes()));
        return new ModelAndView("redirect:/myBookDetails/" + id);
    }
    @RequestMapping(value = "/editAsset/{id}", method = RequestMethod.GET)
    public ModelAndView changeAsset(@PathVariable("id") final int id,@ModelAttribute("assetInstanceForm") final AssetInstanceForm assetInstanceForm) throws AssetInstanceNotFoundException, IOException {
        AssetInstanceImpl assetInstance = assetInstanceService.getAssetInstance(id);
        File file = new File("temp");
        FileUtils.writeByteArrayToFile(file, assetInstance.getImage().getPhoto());

        return new ModelAndView(VIEW_NAME_ASSET_EDIT).addObject("image",file)
                .addObject(ASSET_INSTANCE, assetInstance);
    }

    @ModelAttribute
    public void addAttributes(final Model model) {
        model.addAttribute("path", NAV_BAR_PATH);
    }

}
