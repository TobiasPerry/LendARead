package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
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
    public ModelAndView home() throws UserNotFoundException {
        return init().addObject("table", "my_books").addObject("filter", "all");
    }

    private ModelAndView init() throws UserNotFoundException {
        return initWith(userAssetInstanceService.getUserAssets(userService.getCurrentUser()));
    }

    private ModelAndView initWith(UserAssets userAssets) {
        ModelAndView model = new ModelAndView(registerViewName);
        model.addObject("isLender", !currentUserIsBorrower());
        model.addObject("userAssets", userAssets);
        model.addObject("userEmail", userService.getUser(userService.getCurrentUser()).getName());
        return model;
    }

    private boolean currentUserIsBorrower() {
        return  userService.getCurrentRoles().contains(new SimpleGrantedAuthority("ROLE_BORROWER"));
    }

    @RequestMapping(value ="/applyFilter", method = RequestMethod.GET)
    public ModelAndView changeTab(@RequestParam("table") String table, @RequestParam("filter") String filter) {
        filters.put(table, filter);
        return initWith(userAssetInstanceService.getUserAssets(userService.getCurrentUser()).filter(table, filter))
                .addObject("filter", filter).addObject("table", table);
    }

    @RequestMapping(value = "/changeTable", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") String table) {
        return initWith(userAssetInstanceService.getUserAssets(userService.getCurrentUser()).filter(table, filters.getOrDefault(table, "all")))
                                    .addObject("table", table)
                                    .addObject("filter", filters.getOrDefault(table, "all"));
    }
    @RequestMapping(value = "/sortUserHomeAssets", method = RequestMethod.GET)
    public ModelAndView sortUserHomeAssets(@RequestParam("table") String table,
                                           @RequestParam("attribute") String attribute,
                                           @RequestParam("direction") String direction) {

        ModelAndView modelAndView = initWith(userAssetInstanceService.getUserAssets(userService.getCurrentUser()).sort(table, attribute, direction))
                .addObject("table", table)
                .addObject("filter", filters.getOrDefault(table, "all"));

        modelAndView.addObject("sort_book_name", "book_name".equals(attribute) && "asc".equals(direction));
        modelAndView.addObject("sort_expected_retrieval_date", "expected_retrieval_date".equals(attribute) && "asc".equals(direction));
        modelAndView.addObject("sort_borrower_name", "borrower_name".equals(attribute) && "asc".equals(direction));

        return modelAndView;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", "userHome");
    }

}
