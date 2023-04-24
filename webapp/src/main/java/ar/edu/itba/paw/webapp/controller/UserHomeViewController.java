package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserHomeViewController {
    private final AssetInstanceService assetInstanceService;

    private final AssetAvailabilityService assetAvailabilityService;

    private static final String registerViewName = "views/userHomeView";

    @Autowired
    public UserHomeViewController(AssetInstanceService assetInstanceService, AssetAvailabilityService assetAvailabilityService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;

    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() {


        int pages = 1;
        Page page = assetInstanceService.getAllAssetsInstances(pages);
        List<AssetInstance> lendedBooks = page.getBooks();

        return new ModelAndView(registerViewName).addObject("lendedBooks", lendedBooks)
                                                 .addObject("borrowedBooks", lendedBooks)
                                                 .addObject("myBooks", lendedBooks);
    }

    @RequestMapping(value ="/changeStatus", method = RequestMethod.POST)
    public ModelAndView changeMyBookStatus(@RequestParam("id") int id) {
        AssetInstance assetInstance = assetInstanceService.getAssetInstance(id).get();

        if(assetInstance.getAssetState().canBorrow())
            assetAvailabilityService.setAssetPrivate(id);
        else
            assetAvailabilityService.setAssetPrivate(id);

        return home();
    }

    @RequestMapping(value ="/deleteAsset", method = RequestMethod.POST)
    public ModelAndView deleteAsset(@RequestParam("id") int id) {
        System.out.println(id);
        assetInstanceService.removeAssetInstance(id);
        return home();
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", "userHome");
    }

}
