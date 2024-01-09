package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Language;

import java.util.List;

public interface LanguagesService {
     List<Language> getLanguages();

     Language getLanguage(String code) throws LanguageNotFoundException;

}
