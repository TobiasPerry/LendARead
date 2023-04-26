package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class AssetViewController {
    private final AssetInstanceService assetInstanceService;
    private final AssetAvailabilityService assetAvailabilityService;


    @Autowired
    public AssetViewController(final AssetInstanceService assetInstanceService, final AssetAvailabilityService assetAvailabilityService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView assetInfoView(@RequestParam() int id) {
        Optional<AssetInstance> assetInstanceOpt = assetInstanceService.getAssetInstance(id);

        if (! assetInstanceOpt.isPresent()) {
            return new ModelAndView("/views/notFoundView");
        }

        final ModelAndView mav = new ModelAndView("/views/assetView");
        mav.addObject("assetInstance", assetInstanceOpt.get());

        return mav;
    }

    @RequestMapping(value = "/requestAsset", method = RequestMethod.POST)
    public ModelAndView requestAsset(@RequestParam("assetId") int id){

        boolean borrowRequestSuccessful = assetAvailabilityService.borrowAsset(id, getCurrentUserEmail(), LocalDate.now().plusWeeks(2));

        return new ModelAndView("redirect:/");
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
