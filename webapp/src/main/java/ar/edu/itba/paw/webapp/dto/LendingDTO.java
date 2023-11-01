package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetLendingContext.implementations.LendingImpl;

import javax.ws.rs.core.UriInfo;

public class LendingDTO {






    public static String reference(UriInfo url, LendingImpl lending) {
        return url.getBaseUriBuilder().path("lending").path(String.valueOf(lending.getId())).build().toString();
    }
}
