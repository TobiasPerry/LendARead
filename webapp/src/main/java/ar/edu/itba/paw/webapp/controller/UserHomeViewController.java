package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserHomeViewController {
    private final AssetInstanceService assetInstanceService;

    private final AssetAvailabilityService assetAvailabilityService;

    private final UserAssetInstanceService userAssetInstanceService;

    private final UserService userService;

    private static final String registerViewName = "/views/userHomeView";

    private final Map<String, String> filters = new HashMap<>();

    @Autowired
    public UserHomeViewController(AssetInstanceService assetInstanceService, AssetAvailabilityService assetAvailabilityService, UserAssetInstanceService userAssetInstanceService, UserService userService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() {
        return init().addObject("table", "my_books").addObject("filter", "all");
    }

    private ModelAndView init() {
        ModelAndView model = new ModelAndView(registerViewName);
        model.addObject("isLender", !currentUserIsBorrower());
        model.addObject("userAssets", userAssetInstanceService.getUserAssets(currentUserEmail()));
        model.addObject("userEmail", userService.getUser(currentUserEmail()).get().getName());
        return model;
    }

    //to delete
    private boolean currentUserIsBorrower() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_BORROWER"));
    }

    //to delete
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

        return home();
    }
    @RequestMapping(value ="/applyFilter", method = RequestMethod.GET)
    public ModelAndView changeTab(@RequestParam("table") String table, @RequestParam("filter") String filter) {

        ModelAndView model = new ModelAndView(registerViewName);

        model.addObject("isLender", !currentUserIsBorrower());
        System.out.println(table + filter);
        model.addObject("userAssets", userAssetInstanceService.getUserAssets(currentUserEmail()).filter(table, filter));
        model.addObject("userEmail", userService.getUser(currentUserEmail()).get().getName());
        model.addObject("filter", filter);
        model.addObject("table", table);

        return model;
    }

    @RequestMapping(value ="/showChangeVisibilityModal", method = RequestMethod.POST)
    public ModelAndView showVisibilityModal(@RequestParam("assetId") int assetId) {
        return home().addObject("showSnackbarSucess", "true")
                     .addObject("modalType", "changeBookVisibility").addObject("assetId", assetId);
    }

    @RequestMapping(value ="/deleteAssetModal", method = RequestMethod.POST)
    public ModelAndView showDeleteAssetModal(@RequestParam("assetId") int assetId) {
        return home().addObject("showSnackbarSucess", "true")
                .addObject("modalType", "deleteBook").addObject("assetId", assetId);
    }
    @RequestMapping(value ="/deleteAsset/{id}", method = RequestMethod.POST)
    public ModelAndView deleteAsset(@PathVariable("id") int id) {
        assetInstanceService.removeAssetInstance(id);
        return home();
    }

    @RequestMapping(value = "/changeTable", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") String table, @RequestParam("filter") String filter, @RequestParam("lastTable") String lastTable) {
        filters.put(lastTable, filter);
        return init().addObject("table", table).addObject("filter", filters.getOrDefault(table, "all"));
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", "userHome");
    }

}
