package ar.edu.itba.paw.webapp.miscellaneous;

import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PaginatedData {

    public static <T> void  paginatedData(final Response.ResponseBuilder response,final AbstractPage<T> page,final UriInfo uriInfo) {
        if (page.nextPage()) {
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page.getCurrentPage() + 1).build().toString(), "next");
        }
        if (page.previousPage()) {
            response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page.getCurrentPage() - 1).build().toString(), "prev");
        }
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page.getTotalPages()).build().toString(), "last");
        response.link(uriInfo.getRequestUriBuilder().replaceQueryParam("page", page.getFirstPage()).build().toString(), "first");
    }
}

