package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.webapp.miscellaneous.SortFilterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
final public class UserHomeViewController {

    private final UserAssetInstanceService userAssetInstanceService;

    private final UserService userService;

    private static final String registerViewName = "views/userHomeView/userHomeView";

    private static final String DEFAULT_TABLE_NAME = "my_books";

    private static final String NAV_BAR_PATH = "userHome";

    private static final String IS_LENDER = "isLender", USER_ASSETS = "userAssets", USER_EMAIL = "userEmail", TABLE = "table";

    private static final SortFilterManager sortFilterManager = new SortFilterManager();

    private String currentTable = DEFAULT_TABLE_NAME;

    @Autowired
    public UserHomeViewController(final UserAssetInstanceService userAssetInstanceService, final UserService userService) {
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() throws UserNotFoundException {
        return sortFilterManager.appendTo(currentTable, initialiseModelViewWith(currentTable));
    }
    private ModelAndView initialiseModelViewWith(final String table) throws UserNotFoundException {
        ModelAndView model = new ModelAndView(registerViewName);
        model.addObject(IS_LENDER, !userService.getCurrentUserIsBorrower());
        model.addObject(USER_ASSETS, getUserAssetsIn(table));
        model.addObject(USER_EMAIL, userService.getUser(userService.getCurrentUser()).getName());
        model.addObject(TABLE, table);
        return model;
    }


    @RequestMapping(value ="/applyFilter", method = RequestMethod.GET)
    public ModelAndView changeTab(@RequestParam("table") final String table, @RequestParam("filterValue") final String filterValue, @RequestParam("filterAtribuite") final String filterAtribuite) throws UserNotFoundException {
        sortFilterManager.updateSelectedFilter(table, filterAtribuite, filterValue);
        return sortFilterManager.appendTo(table,  initialiseModelViewWith(table));
    }

    @RequestMapping(value = "/userHomeTab", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") final String table) throws UserNotFoundException {
        currentTable = table;
        return sortFilterManager.appendTo(table, initialiseModelViewWith(table));
    }

    private List<? extends AssetInstance> getUserAssetsIn(final String table) {
        return userAssetInstanceService.getUserAssetsOfTable( userService.getCurrentUser(), table,
                sortFilterManager.getFilterAttribuite(table), sortFilterManager.getFilterValue(table),
                sortFilterManager.getSortAtribuite(table), sortFilterManager.getSortDirection(table));
    }

    @RequestMapping(value = "/sortUserHomeAssets", method = RequestMethod.GET)
    public ModelAndView sortUserHomeAssets(@RequestParam("table") final String table,
                                           @RequestParam("attribute") final String attribute,
                                           @RequestParam("direction") final String direction) throws UserNotFoundException {

        sortFilterManager.updateSelectedSort(table, attribute, direction);
        return sortFilterManager.appendTo(table, initialiseModelViewWith(table));
    }

    @ModelAttribute
    public void addAttributes(final Model model) {
        model.addAttribute("path", NAV_BAR_PATH);
    }

}
