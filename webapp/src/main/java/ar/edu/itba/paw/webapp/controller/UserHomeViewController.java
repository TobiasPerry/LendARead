package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.AssetInstanceNotFoundException;
import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.AssetAvailabilityService;
import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import ar.edu.itba.paw.webapp.miscellaneous.SortFilterManager;
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

    private final UserAssetInstanceService userAssetInstanceService;

    private final UserService userService;

    private static final String registerViewName = "/views/userHomeView";

    private static final SortFilterManager sortFilterManager = new SortFilterManager();

    @Autowired
    public UserHomeViewController(UserAssetInstanceService userAssetInstanceService, UserService userService) {
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() throws UserNotFoundException {
        return sortFilterManager.appendSelectedSort("my_books", init().addObject("table", "my_books").addObject("filter", "none"));
    }

    private ModelAndView init() throws UserNotFoundException {
        return initWith(getUserAssets("my_books"));
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
    public ModelAndView changeTab(@RequestParam("table") String table, @RequestParam("filterValue") String filterValue, @RequestParam("filterAtribuite") String filterAtribuite) throws UserNotFoundException {

        sortFilterManager.updateSelectedFilter(table, filterAtribuite, filterValue);

        ModelAndView model = initWith(getUserAssets(table)).addObject("filter", filterValue).addObject("table", table);

        return sortFilterManager.appendSelectedSort(table, model);
    }

    @RequestMapping(value = "/changeTable", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") String table) throws UserNotFoundException {
        ModelAndView model = initWith(getUserAssets(table))
                                    .addObject("table", table)
                                    .addObject("table", table);

        return sortFilterManager.appendSelectedFilter(table, sortFilterManager.appendSelectedSort(table, model));
    }

    private UserAssets getUserAssets(String table) {
        return userAssetInstanceService.getUserAssets(
                userService.getCurrentUser(), table,
                sortFilterManager.getFilterAttribuite(table), sortFilterManager.getFilterValue(table),
                sortFilterManager.getSortAtribuite(table), sortFilterManager.getSortDirection(table));
    }

    @RequestMapping(value = "/sortUserHomeAssets", method = RequestMethod.GET)
    public ModelAndView sortUserHomeAssets(@RequestParam("table") String table,
                                           @RequestParam("attribute") String attribute,
                                           @RequestParam("direction") String direction) throws UserNotFoundException {

        sortFilterManager.updateSelectedSort(table, attribute, direction);

        ModelAndView modelAndView =   initWith(getUserAssets(table)).addObject("table", table);
//
//        modelAndView.addObject("sort_book_name", "book_name".equals(attribute) && "asc".equals(direction));
//        modelAndView.addObject("sort_expected_retrieval_date", "expected_retrieval_date".equals(attribute) && "asc".equals(direction));
//        modelAndView.addObject("sort_borrower_name", "borrower_name".equals(attribute) && "asc".equals(direction));

        return sortFilterManager.appendSelectedSort(table, sortFilterManager.appendSelectedFilter(table, modelAndView));
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", "userHome");
    }

}
