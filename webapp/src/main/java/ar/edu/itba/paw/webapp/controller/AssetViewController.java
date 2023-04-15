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
        Optional<AssetInstance> assetInstanceOpt = this.assetInstanceService.getAssetInstance(id);
        if (! assetInstanceOpt.isPresent()) {
            System.out.println("Not found");
            return new ModelAndView("/views/notFoundView");
        }
        AssetInstance assetInstance = assetInstanceOpt.get();
        final ModelAndView mav = new ModelAndView("/views/assetView");
        mav.addObject("imageId", assetInstance.getImageId());
        mav.addObject("name", assetInstance.getBook().getName());
        mav.addObject("author", assetInstance.getBook().getAuthor());
        mav.addObject("language", assetInstance.getBook().getLanguage());
        mav.addObject("isbn", assetInstance.getBook().getIsbn());
        mav.addObject("physicalCondition", assetInstance.getAssetState());
        mav.addObject("locationPC", assetInstance.getLocation().getZipcode());
        mav.addObject("location", assetInstance.getLocation().getLocality());
        mav.addObject("province", assetInstance.getLocation().getProvince());
        mav.addObject("country", assetInstance.getLocation().getCountry());
        mav.addObject("id", assetInstance.getId());

        return mav;
    }
}
