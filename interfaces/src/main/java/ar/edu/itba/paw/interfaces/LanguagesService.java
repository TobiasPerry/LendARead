package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.Language;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;

import java.util.Optional;

public interface LanguagesService {
      AbstractPage<Language> getLanguages(final int page, final int itemsPerPage, final Boolean isUsed);
     Optional<Language> getLanguage(String code) ;

}
