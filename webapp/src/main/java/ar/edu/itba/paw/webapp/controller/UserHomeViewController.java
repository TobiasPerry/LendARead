package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.UserAssetInstanceService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.assetExistanceContext.interfaces.AssetInstance;
import ar.edu.itba.paw.models.viewsContext.interfaces.PageUserAssets;
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

    private static final String IS_LENDER = "isLender", USER_ASSETS = "userAssets", USER_EMAIL = "userEmail", TABLE = "table", CURRENT_PAGE = "currentPage";

    private static final String FILTER_ATRIBUITE = "filterAtribuite", SORT_ATRIBUITE = "attribute", SORT_VALUE ="direction", FILTER_VALUE = "filterValue";


    private static final int PAGE_NUMBER = 5, DEFAULT_PAGE_NUMBER = 1;

    @Autowired
    public UserHomeViewController(final UserAssetInstanceService userAssetInstanceService, final UserService userService) {
        this.userAssetInstanceService = userAssetInstanceService;
        this.userService = userService;
    }

    @RequestMapping(value = "/userHome", method = RequestMethod.GET)
    public ModelAndView home() throws UserNotFoundException {
        return initialiseModelViewWith(DEFAULT_PAGE_NUMBER, DEFAULT_TABLE_NAME, NONE, NONE, NONE, NONE);
    }
    private ModelAndView initialiseModelViewWith(final int currentPage, final String table,  final String sortAtribuite, final String sortValue, final String filterAtribuite, final String filterValue) throws UserNotFoundException {
        ModelAndView model = new ModelAndView(registerViewName);
        PageUserAssets page = getUserAssetsIn(currentPage, table, sortAtribuite, sortValue, filterAtribuite, filterValue);
        model.addObject(IS_LENDER, !userService.getCurrentUserIsBorrower());
        model.addObject(USER_ASSETS, page.getUserAssets());
        model.addObject(USER_EMAIL, userService.getUser(userService.getCurrentUser()).getName());
        model.addObject(TABLE, table);

        model.addObject("filter", filterValue);
        model.addObject("sort_" + sortAtribuite, "asc".equals(sortValue));

        model.addObject(SORT_ATRIBUITE, sortAtribuite);
        model.addObject(SORT_VALUE, sortValue);
        model.addObject(FILTER_VALUE, filterValue);
        model.addObject(FILTER_ATRIBUITE, filterAtribuite);


        model.addObject("nextPage", page.nextPage());
        model.addObject("previousPage", page.previousPage());
        model.addObject(CURRENT_PAGE, page.getCurrentPage());
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("page", page.getCurrentPage());

        return model;
    }

    @RequestMapping(value ="/applyFilter", method = RequestMethod.GET)
    public ModelAndView changeTab( @RequestParam(CURRENT_PAGE) final int currentPage,
                                   @RequestParam(TABLE) final String table,
                                   @RequestParam(SORT_ATRIBUITE) final String attribute,
                                   @RequestParam(SORT_VALUE) final String direction,
                                   @RequestParam(FILTER_VALUE) final String filterValue,
                                   @RequestParam(FILTER_ATRIBUITE) final String filterAtribuite) throws UserNotFoundException {

        return initialiseModelViewWith(currentPage, table, attribute, direction, filterAtribuite, filterValue);
    }

    @RequestMapping(value = "/userHomeTab", method = RequestMethod.GET)
    public ModelAndView changeTable(@RequestParam("type") final String table) throws UserNotFoundException {
        return initialiseModelViewWith(DEFAULT_PAGE_NUMBER, table, NONE, NONE, NONE, NONE);
    }

    private PageUserAssets getUserAssetsIn(final int currentPage, final String table, final String sortAtribuite, final String sortValue, final String filterAtribuite, final String filterValue) {
        return userAssetInstanceService.getUserAssetsOfTable(PAGE_NUMBER, currentPage, userService.getCurrentUser(), table, filterAtribuite, filterValue, sortAtribuite, sortValue);
    }

    @RequestMapping(value = "/sortUserHomeAssets", method = RequestMethod.GET)
    public ModelAndView sortUserHomeAssets( @RequestParam(CURRENT_PAGE) final int currentPage,
                                            @RequestParam(TABLE) final String table,
                                            @RequestParam(SORT_ATRIBUITE) final String attribute,
                                            @RequestParam(SORT_VALUE) final String direction,
                                            @RequestParam(FILTER_VALUE) final String filterValue,
                                            @RequestParam(FILTER_ATRIBUITE) final String filterAtribuite) throws UserNotFoundException {
        return initialiseModelViewWith(currentPage, table, attribute, direction, filterAtribuite, filterValue);
    }

    @ModelAttribute
    public void addAttributes(final Model model) {
        model.addAttribute("path", NAV_BAR_PATH);
    }

}
