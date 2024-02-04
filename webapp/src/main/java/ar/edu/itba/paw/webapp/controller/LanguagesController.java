package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.edu.itba.paw.models.assetExistanceContext.Language;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.webapp.dto.LanguagesDTO;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import ar.edu.itba.paw.webapp.miscellaneous.PaginatedData;
import ar.edu.itba.paw.webapp.miscellaneous.StaticCache;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Component
@Path(EndpointsUrl.Languages_URL)
public class LanguagesController {

    private final LanguagesService ls;
    @Context
    private UriInfo uriInfo;

    @Autowired
    public LanguagesController(final LanguagesService ls) {
        this.ls = ls;
    }

    @GET
    @Produces(value = {Vnd.VND_LANGUAGE})
    public Response getUserAssetsInstances(final @QueryParam("page") @DefaultValue("1") int page,
                                           final @QueryParam("itemsPerPage") @DefaultValue("500") int pageSize,
                                           final @QueryParam("isUsed") Boolean isUsed
    ) {
        AbstractPage<Language> languages = ls.getLanguages(page, pageSize, isUsed);
        if (languages.getTotalPages() == 0 || languages.getList().isEmpty()) {
            return Response.noContent().build();
        }
        List<LanguagesDTO> languagesDTOS = LanguagesDTO.fromLanguages(languages.getList());
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<LanguagesDTO>>(languagesDTOS) {
        });
        PaginatedData.paginatedData(response, languages, uriInfo);
        StaticCache.setUnconditionalCache(response);
        return response.build();
    }

    @GET
    @Path("/{code}")
    @Produces(value = {Vnd.VND_LANGUAGE})
    public Response getUserAssetsInstances(final @PathParam("code") String code) throws LanguageNotFoundException {
        Language language = ls.getLanguage(code).orElseThrow(LanguageNotFoundException::new);
        LanguagesDTO languagesDTO = LanguagesDTO.fromLanguage(language);
        Response.ResponseBuilder response = Response.ok(languagesDTO);
        StaticCache.setUnconditionalCache(response);
        return response.build();
    }
}
