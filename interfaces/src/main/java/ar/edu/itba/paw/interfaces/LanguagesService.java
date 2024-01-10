package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Language;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;

public interface LanguagesService {
      AbstractPage<Language> getLanguages(final int page, final int itemsPerPage, final Boolean isUsed);
     Language getLanguage(String code) throws LanguageNotFoundException;

}
