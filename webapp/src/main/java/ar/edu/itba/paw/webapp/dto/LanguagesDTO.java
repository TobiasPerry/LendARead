package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.assetExistanceContext.Language;
import ar.edu.itba.paw.webapp.miscellaneous.EndpointsUrl;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.core.UriInfo;
import java.util.List;

@Getter
@Setter
public class LanguagesDTO {
    private String code;
    private String name;

   public static LanguagesDTO fromLanguage(final Language language) {
        LanguagesDTO languagesDTO = new LanguagesDTO();
        languagesDTO.code = language.getCode();
        languagesDTO.name = language.getName();
        return languagesDTO;
    }
    public static List<LanguagesDTO> fromLanguages(final List<Language> languages) {
        return languages.stream().map(LanguagesDTO::fromLanguage).collect(java.util.stream.Collectors.toList());
    }
    public static String reference(UriInfo url, Language language) {
        return url.getBaseUriBuilder().path(EndpointsUrl.Languages_URL).path(String.valueOf(language.getCode())).build().toString();
    }



}
