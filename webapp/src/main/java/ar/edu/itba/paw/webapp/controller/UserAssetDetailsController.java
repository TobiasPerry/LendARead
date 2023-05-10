package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAssetDetailsController {

    private final AssetInstanceService assetInstanceService;
    private final AssetAvailabilityService assetAvailabilityService;

    private  String table = "";

    public UserAssetDetailsController(AssetInstanceService assetInstanceService, AssetAvailabilityService assetAvailabilityService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
    }

    @RequestMapping(value = "/userHomeReturn", method = RequestMethod.GET)
    public ModelAndView returnUserHome() {
        return new ModelAndView("redirect:/userHome");
    }
    @RequestMapping(value = "/userBookDetails/{assetId}", method = RequestMethod.GET)
    public ModelAndView userAssetDetails(@PathVariable(name = "assetId") int assetId, @RequestParam("table") String table) throws AssetInstanceNotFoundException {
        this.table = table;
        return new ModelAndView("/views/userBookDetails")
                    .addObject("asset", assetInstanceService.getAssetInstance(assetId))
                .addObject("table", table);
    }

    @RequestMapping(value ="/showChangeVisibilityModal", method = RequestMethod.POST)
    public ModelAndView showVisibilityModal(@RequestParam("assetId") int assetId) throws AssetInstanceNotFoundException {
        return userAssetDetails(assetId, this.table).addObject("showSnackbarSucess", "true")
                .addObject("modalType", "changeBookVisibility")
                .addObject("assetId", assetId);
    }

    @RequestMapping(value ="/deleteAssetModal", method = RequestMethod.POST)
    public ModelAndView showDeleteAssetModal(@RequestParam("assetId") int assetId) throws AssetInstanceNotFoundException {
        return  userAssetDetails(assetId, this.table).addObject("showSnackbarSucess", "true")
                .addObject("modalType", "deleteBook")
                .addObject("assetId", assetId);
    }
    @RequestMapping(value ="/deleteAsset/{id}", method = RequestMethod.POST)
    public ModelAndView deleteAsset(@PathVariable("id") int id) throws AssetInstanceNotFoundException{
        assetInstanceService.removeAssetInstance(id);
        return new ModelAndView("redirect:/userHome");
    }

    @RequestMapping(value ="/changeStatus", method = RequestMethod.POST)
    public ModelAndView changeMyBookStatus(@RequestParam("id") int id) throws AssetInstanceNotFoundException {
        AssetInstance assetInstance = assetInstanceService.getAssetInstance(id);

        if(assetInstance.getAssetState().isPublic())
            assetAvailabilityService.setAssetPrivate(id);
        else if(assetInstance.getAssetState().isPrivate())
            assetAvailabilityService.setAssetPublic(id);

        return userAssetDetails(id, this.table);
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", "userHome");
    }

}
