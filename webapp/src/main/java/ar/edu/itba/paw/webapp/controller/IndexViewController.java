package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AssetInstanceService;
import ar.edu.itba.paw.models.viewsContext.implementations.SearchQueryImpl;
import ar.edu.itba.paw.models.viewsContext.implementations.Sort;
import ar.edu.itba.paw.models.viewsContext.implementations.SortDirection;
import ar.edu.itba.paw.models.viewsContext.interfaces.Page;
import ar.edu.itba.paw.webapp.form.SearchFilterSortForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexViewController {
    private final ViewResolver viewResolver;
    private final AssetInstanceService assetInstanceService;

    @Autowired
    public IndexViewController(@Qualifier("viewResolver") final ViewResolver vr, final AssetInstanceService assetInstanceService) {
        this.viewResolver = vr;
        this.assetInstanceService = assetInstanceService;
    }


    @RequestMapping("/")
    public ModelAndView indexView() {
        Page page = assetInstanceService.getAllAssetsInstances(
                1, 4, new SearchQueryImpl(new ArrayList<>(), new ArrayList<>(), "", Sort.RECENT, SortDirection.DESCENDING)
        );

        ModelAndView mav = new ModelAndView("/views/index");
        mav.addObject("books", page.getBooks());

        return mav;
    }


    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
    public ModelAndView discoveryView(
            final @Valid @ModelAttribute("searchFilterSortForm") SearchFilterSortForm searchFilterSortForm,
            final BindingResult errors
    ) {

        if (errors.hasErrors())
            return new ModelAndView("/views/discoveryView");

        int pageNum = searchFilterSortForm.getCurrentPage();
        final int itemsPerPage = 15;

        Page page = assetInstanceService.getAllAssetsInstances(
                pageNum, itemsPerPage,
                new SearchQueryImpl(
                        (searchFilterSortForm.getLanguages() != null) ? searchFilterSortForm.getLanguages() : new ArrayList<>(),
                        (searchFilterSortForm.getPhysicalConditions() != null) ? searchFilterSortForm.getPhysicalConditions() : new ArrayList<>(),
                        (searchFilterSortForm.getSearch() != null) ? searchFilterSortForm.getSearch() : "",
                        (searchFilterSortForm.getSort() != null) ? Sort.fromString(searchFilterSortForm.getSort()) : Sort.RECENT,
                        (searchFilterSortForm.getSortDirection() != null) ? SortDirection.fromString(searchFilterSortForm.getSortDirection()) : SortDirection.DESCENDING
                )
        );

        final ModelAndView mav = new ModelAndView("/views/discoveryView");
        mav.addObject("path", "home");
        mav.addObject("books", page.getBooks());
        mav.addObject("nextPage", page.getCurrentPage() != page.getTotalPages());
        mav.addObject("previousPage", page.getCurrentPage() != 1);
        mav.addObject("currentPage", page.getCurrentPage());
        mav.addObject("totalPages", page.getTotalPages());

        List<String> languages = page.getLanguages();
        mav.addObject("languages", languages != null ? languages : new ArrayList<>());
        mav.addObject("languagesFiltered", (searchFilterSortForm.getLanguages() != null) ? searchFilterSortForm.getLanguages() : new ArrayList<>());

        List<String> physicalConditions = page.getPhysicalConditions();
        mav.addObject("physicalConditions", physicalConditions != null ? physicalConditions : new ArrayList<>());
        mav.addObject("physicalConditionsFiltered", (searchFilterSortForm.getPhysicalConditions() != null) ? searchFilterSortForm.getPhysicalConditions() : new ArrayList<>());

        mav.addObject("search", (searchFilterSortForm.getSearch() != null) ? searchFilterSortForm.getSearch() : "");

        // Recent and descending is the default
        mav.addObject("sort", (searchFilterSortForm.getSort() != null) ? Sort.fromString(searchFilterSortForm.getSort()) : Sort.RECENT);
        mav.addObject("sortDirection", (searchFilterSortForm.getSortDirection() != null) ? SortDirection.fromString(searchFilterSortForm.getSortDirection()) : SortDirection.DESCENDING);

        mav.addObject("itemsPerPage", itemsPerPage);

        return mav;
    }


    @RequestMapping("/assetView")
    public ModelAndView assetView() {
        final ModelAndView mav = new ModelAndView("/views/assetView");
        return mav;
    }

}
