package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String DEFAULT_TABLE_NAME = "my_books", NONE = "none";

    private static final String NAV_BAR_PATH = "userHome";

    private static final String IS_LENDER = "isLender", USER_ASSETS = "userAssets", USER_EMAIL = "userEmail", TABLE = "table";

    @Autowired
    public UserHomeViewController(final UserAssetInstanceService userAssetInstanceService, final UserService userService) {
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() throws UserNotFoundException {
        return initialiseModelViewWith(DEFAULT_TABLE_NAME, NONE, NONE, NONE, NONE);
    }
    private ModelAndView initialiseModelViewWith(final String table,  final String sortAtribuite, final String sortValue, final String filterAtribuite, final String filterValue) throws UserNotFoundException {
        ModelAndView model = new ModelAndView(registerViewName);
        model.addObject(IS_LENDER, !userService.getCurrentUserIsBorrower());
        model.addObject(USER_ASSETS, getUserAssetsIn(table, sortAtribuite, sortValue, filterAtribuite, filterValue));
        model.addObject(USER_EMAIL, userService.getUser(userService.getCurrentUser()).getName());
        model.addObject(TABLE, table);

        model.addObject("filter", filterValue);
        model.addObject("sort_" + sortAtribuite, "asc".equals(sortValue));

        model.addObject("attribute", sortAtribuite);
        model.addObject("direction", sortValue);
        model.addObject("filterValue", filterValue);
        model.addObject("filterAtribuite", filterAtribuite);

        return model;
    }

    @RequestMapping(value ="/applyFilter", method = RequestMethod.GET)
    public ModelAndView changeTab(@RequestParam("table") final String table,
                                  @RequestParam("attribute") final String attribute,
                                  @RequestParam("direction") final String direction,
                                  @RequestParam("filterValue") final String filterValue,
                                  @RequestParam("filterAtribuite") final String filterAtribuite) throws UserNotFoundException {

        return initialiseModelViewWith(table, attribute, direction, filterAtribuite, filterValue);
    }

    @RequestMapping(value = "/userHomeTab", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") final String table) throws UserNotFoundException {
        return initialiseModelViewWith(table, NONE, NONE, NONE, NONE);
    }

    private List<? extends AssetInstance> getUserAssetsIn(final String table, final String sortAtribuite, final String sortValue, final String filterAtribuite, final String filterValue) {
        return userAssetInstanceService.getUserAssetsOfTable( userService.getCurrentUser(), table, filterAtribuite, filterValue, sortAtribuite, sortValue);
    }

    @RequestMapping(value = "/sortUserHomeAssets", method = RequestMethod.GET)
    public ModelAndView sortUserHomeAssets(@RequestParam("table") final String table,
                                           @RequestParam("attribute") final String attribute,
                                           @RequestParam("direction") final String direction,
                                           @RequestParam("filterValue") final String filterValue,
                                           @RequestParam("filterAtribuite") final String filterAtribuite) throws UserNotFoundException {
        return initialiseModelViewWith(table, attribute, direction, filterAtribuite, filterValue);
    }

    @ModelAttribute
    public void addAttributes(final Model model) {
        model.addAttribute("path", NAV_BAR_PATH);
    }

}
