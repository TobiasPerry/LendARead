package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.webapp.form.BorrowAssetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class AssetViewController {
    AssetInstanceService assetInstanceService;


    @Autowired
    public AssetViewController(final AssetInstanceService assetInstanceService) {
        this.assetInstanceService = assetInstanceService;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ModelAndView assetInfoView(@RequestParam() int id,@ModelAttribute("borrowAssetForm") final BorrowAssetForm form) {
        HashMap<String, String> info = this.assetInstanceService.getAssetInstanceDisplay(id);
        if (info == null) {
            System.out.println("Not found");
            return new ModelAndView("/views/notFoundView");
        }
        final ModelAndView mav = new ModelAndView("/views/assetView");
        for (String key : info.keySet()) {
            mav.addObject(key, info.get(key));
        }
        return mav;
    }
}
