package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;

import java.util.List;

public interface LanguagesService {
    public List<LanguageImpl> getLanguages();
}
