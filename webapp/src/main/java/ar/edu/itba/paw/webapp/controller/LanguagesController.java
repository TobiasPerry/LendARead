package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Language;
import ar.edu.itba.paw.webapp.dto.LanguagesDTO;
import ar.edu.itba.paw.webapp.miscellaneous.Vnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/api/languages")
public class LanguagesController {

    private final LanguagesService ls;

    @Autowired
    public LanguagesController(final LanguagesService ls) {
        this.ls = ls;
    }
    @GET
    @Produces(value = {Vnd.VND_LANGUAGE})
    public Response getUserAssetsInstances()  {
        List<Language> languages = ls.getLanguages();
        List<LanguagesDTO> languagesDTOS = LanguagesDTO.fromLanguages(languages);
        return Response.ok(new GenericEntity<List<LanguagesDTO>>(languagesDTOS) {}).build();
    }
    @GET
    @Path("/{code}")
    @Produces(value = {Vnd.VND_LANGUAGE})
    public Response getUserAssetsInstances(final @PathParam("code") String code) throws LanguageNotFoundException {
        Language language = ls.getLanguage(code);
        LanguagesDTO languagesDTO = LanguagesDTO.fromLanguage(language);
        return Response.ok(languagesDTO).build();
    }
}
