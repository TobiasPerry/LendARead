package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserHomeViewController {
    private final AssetInstanceService assetInstanceService;

    private final AssetAvailabilityService assetAvailabilityService;

    private final UserAssetInstanceService userAssetInstanceService;

    private static final String registerViewName = "/views/userHomeView";

    @Autowired
    public UserHomeViewController(AssetInstanceService assetInstanceService, AssetAvailabilityService assetAvailabilityService, UserAssetInstanceService userAssetInstanceService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
        this.userAssetInstanceService = userAssetInstanceService;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() {
        return init().addObject("table", "my_books");
    }

    private ModelAndView init() {
        ModelAndView model = new ModelAndView(registerViewName);
        model.addObject("isLender", !currentUserIsBorrower());
        model.addObject("userAssets", userAssetInstanceService.getUserAssets(currentUserEmail()));
        return model;
    }

    private boolean currentUserIsBorrower() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_BORROWER"));
    }

    private String currentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @RequestMapping(value ="/changeStatus", method = RequestMethod.POST)
    public ModelAndView changeMyBookStatus(@RequestParam("id") int id) {
        AssetInstance assetInstance = assetInstanceService.getAssetInstance(id).get();

        if(assetInstance.getAssetState().isPublic())
            assetAvailabilityService.setAssetPrivate(id);
        else if(assetInstance.getAssetState().isPrivate())
            assetAvailabilityService.setAssetPublic(id);

        return home().addObject("showSnackbarSucess", "true");
    }

    @RequestMapping(value ="/deleteAsset/{id}", method = RequestMethod.POST)
    public ModelAndView deleteAsset(@PathVariable("id") int id) {
        assetInstanceService.removeAssetInstance(id);
        return home();
    }

    @RequestMapping(value = "/changeTable", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") String table) {
        return init().addObject("table", table);
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", "userHome");
    }

}
