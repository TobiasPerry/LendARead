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
    private final Map<String, SortOption> sorts = new HashMap<>();

    static class SortOption {
       private final String attribuite;
       private final String direction;

        public SortOption(String attribuite, String direction) {
            this.attribuite = attribuite;
            this.direction = direction;
        }
    }

    private final static SortOption EmptySortOption = new SortOption("none", "none");

    @Autowired
    public UserHomeViewController(AssetInstanceService assetInstanceService, AssetAvailabilityService assetAvailabilityService, UserAssetInstanceService userAssetInstanceService, UserService userService) {
        this.assetInstanceService = assetInstanceService;
        this.assetAvailabilityService = assetAvailabilityService;
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() throws UserNotFoundException {
        return appendSelectedSort("my_books", init().addObject("table", "my_books").addObject("filter", "all"));
    }

    private ModelAndView init() throws UserNotFoundException {
        return initWith(userAssetInstanceService.getUserAssets(userService.getCurrentUser(), "my_books", "all", "none", "none"));
    }

    private ModelAndView initWith(UserAssets userAssets) throws UserNotFoundException {
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
    public ModelAndView changeTab(@RequestParam("table") String table, @RequestParam("filter") String filter) throws UserNotFoundException {
        filters.put(table, filter);
        ModelAndView model = initWith(userAssetInstanceService.getUserAssets(userService.getCurrentUser(), table, filter, sorts.getOrDefault(table, EmptySortOption).attribuite,  sorts.getOrDefault(table, EmptySortOption).direction))
                .addObject("filter", filter).addObject("table", table);

        return appendSelectedSort(table, model);
    }

    @RequestMapping(value = "/changeTable", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") String table) throws UserNotFoundException {
        ModelAndView model = initWith(userAssetInstanceService.getUserAssets(userService.getCurrentUser(), table, filters.getOrDefault(table, "all"), sorts.getOrDefault(table, EmptySortOption).attribuite,  sorts.getOrDefault(table, EmptySortOption).direction))
                                    .addObject("table", table)
                                    .addObject("filter", filters.getOrDefault(table, "all"))
                                    .addObject("table", table);

        return appendSelectedSort(table, model);
    }

    private ModelAndView appendSelectedSort(String table, ModelAndView model) {
        return model.addObject("sort_" + sorts.getOrDefault(table, EmptySortOption).attribuite, "asc".equals(sorts.getOrDefault(table, EmptySortOption).direction));
    }
    @RequestMapping(value = "/sortUserHomeAssets", method = RequestMethod.GET)
    public ModelAndView sortUserHomeAssets(@RequestParam("table") String table,
                                           @RequestParam("attribute") String attribute,
                                           @RequestParam("direction") String direction) throws UserNotFoundException {

        sorts.put(table, new SortOption(attribute, direction));
        ModelAndView modelAndView =   initWith(userAssetInstanceService.getUserAssets(userService.getCurrentUser(), table, filters.getOrDefault(table, "all"), sorts.getOrDefault(table, EmptySortOption).attribuite,  sorts.getOrDefault(table, EmptySortOption).direction))
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
