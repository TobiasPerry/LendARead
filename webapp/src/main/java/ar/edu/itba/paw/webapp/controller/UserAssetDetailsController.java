package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAssetDetailsController {

    @RequestMapping(value = "/userBookDetails/{assetId}", method = RequestMethod.GET)
    public ModelAndView userAssetDetails(@PathVariable(name = "assetId") String assetId) {
        return new ModelAndView("/views/userBookDetails");
    }

}
