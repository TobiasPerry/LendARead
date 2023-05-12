package ar.edu.itba.paw.webapp.miscellaneous;

import ar.edu.itba.paw.webapp.controller.UserHomeViewController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

final public class SortFilterManager {

    private final Map<String, FilterOption> filters = new HashMap<>();
    private final Map<String, SortOption> sorts = new HashMap<>();

    static class SortOption {
        private final String attribuite;
        private final String direction;

        public SortOption(String attribuite, String direction) {
            this.attribuite = attribuite;
            this.direction = direction;
        }
    }

    static class FilterOption {
        private final String attribuite;

        private final String value;

        public FilterOption(String attribuite, String value) {
            this.attribuite = attribuite;
            this.value = value;
        }
    }

    private final static SortOption EmptySortOption = new SortOption("none", "none");
    private final static FilterOption EmptyFilterOption = new FilterOption("none", "none");
    public void updateSelectedFilter(String table, String filterAtribuite, String filterValue) {
        filters.put(table, new FilterOption(filterAtribuite, filterValue));
    }

    public void updateSelectedSort(String table, String sortAtribuite, String sortDirection) {
        sorts.put(table, new SortOption(sortAtribuite, sortDirection));
    }
    public ModelAndView appendSelectedFilter(String table, ModelAndView model) {
        return model.addObject("filter", filters.getOrDefault(table, EmptyFilterOption).value);
    }

    public ModelAndView appendTo(String table, ModelAndView model) {
        return appendSelectedSort(table, appendSelectedFilter(table, model));
    }

    public ModelAndView appendSelectedSort(String table, ModelAndView model) {
        return model.addObject("sort_" + sorts.getOrDefault(table, EmptySortOption).attribuite, "asc".equals(sorts.getOrDefault(table, EmptySortOption).direction));
    }

    public String getFilterAttribuite(String table) {
        return filters.getOrDefault(table, EmptyFilterOption).attribuite;
    }

    public String getFilterValue(String table) {
        return filters.getOrDefault(table, EmptyFilterOption).value;
    }

    public String getSortAtribuite(String table) {
        return sorts.getOrDefault(table, EmptySortOption).attribuite;
    }

    public String getSortDirection(String table) {
        return  sorts.getOrDefault(table, EmptySortOption).direction;
    }
}
