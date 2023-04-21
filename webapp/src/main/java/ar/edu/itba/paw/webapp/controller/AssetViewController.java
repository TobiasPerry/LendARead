package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Optional;

@Controller
public class AssetViewController {
    AssetInstanceService assetInstanceService;


    @Autowired
    public AssetViewController(final AssetInstanceService assetInstanceService) {
        this.assetInstanceService = assetInstanceService;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView assetInfoView(@RequestParam() int id,@ModelAttribute("borrowAssetForm") final BorrowAssetForm form) {
        Optional<AssetInstance> assetInstanceOpt = assetInstanceService.getAssetInstance(id);

        if (! assetInstanceOpt.isPresent()) {
            return new ModelAndView("/views/notFoundView");
        }

        final ModelAndView mav = new ModelAndView("/views/assetView");
        mav.addObject("assetInstance", assetInstanceOpt.get());

        return mav;
    }
}
