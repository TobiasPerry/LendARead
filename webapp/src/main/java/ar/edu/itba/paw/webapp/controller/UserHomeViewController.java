package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.userContext.interfaces.UserAssets;
import ar.edu.itba.paw.webapp.miscellaneous.SortFilterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserHomeViewController {

    private final UserAssetInstanceService userAssetInstanceService;

    private final UserService userService;

    private static final String registerViewName = "/views/userHomeView";

    private static final String DEFAULT_TABLE_NAME = "my_books";

    private static final String NAV_BAR_PATH = "userHome";

    private static final SortFilterManager sortFilterManager = new SortFilterManager();

    private String currentTable = DEFAULT_TABLE_NAME;

    @Autowired
    public UserHomeViewController(UserAssetInstanceService userAssetInstanceService, UserService userService) {
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() throws UserNotFoundException {
        return sortFilterManager.appendTo(currentTable, initialiseModelViewWith(currentTable));
    }
    private ModelAndView initialiseModelViewWith(String table) throws UserNotFoundException {
        ModelAndView model = new ModelAndView(registerViewName);
        model.addObject("isLender", !currentUserIsBorrower());
        model.addObject("userAssets", getUserAssetsIn(table));
        model.addObject("userEmail", userService.getUser(userService.getCurrentUser()).getName());
        model.addObject("table", table);
        return model;
    }

    private boolean currentUserIsBorrower() {
        return  userService.getCurrentRoles().contains(new SimpleGrantedAuthority("ROLE_BORROWER"));
    }

    @RequestMapping(value ="/applyFilter", method = RequestMethod.GET)
    public ModelAndView changeTab(@RequestParam("table") String table, @RequestParam("filterValue") String filterValue, @RequestParam("filterAtribuite") String filterAtribuite) throws UserNotFoundException {
        sortFilterManager.updateSelectedFilter(table, filterAtribuite, filterValue);
        return sortFilterManager.appendTo(table,  initialiseModelViewWith(table));
    }

    @RequestMapping(value = "/changeTable", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") String table) throws UserNotFoundException {
        currentTable = table;
        return sortFilterManager.appendTo(table, initialiseModelViewWith(table));
    }

    private List<? extends AssetInstance> getUserAssetsIn(String table) {
        return userAssetInstanceService.getUserAssetsOfTable( userService.getCurrentUser(), table,
                sortFilterManager.getFilterAttribuite(table), sortFilterManager.getFilterValue(table),
                sortFilterManager.getSortAtribuite(table), sortFilterManager.getSortDirection(table));
    }

    @RequestMapping(value = "/sortUserHomeAssets", method = RequestMethod.GET)
    public ModelAndView sortUserHomeAssets(@RequestParam("table") String table,
                                           @RequestParam("attribute") String attribute,
                                           @RequestParam("direction") String direction) throws UserNotFoundException {

        sortFilterManager.updateSelectedSort(table, attribute, direction);
        return sortFilterManager.appendTo(table, initialiseModelViewWith(table));
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("path", NAV_BAR_PATH);
    }

}
