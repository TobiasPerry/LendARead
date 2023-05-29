package ar.itba.edu.paw.persistenceinterfaces;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;

import java.util.List;
import java.util.Optional;

public interface LanguageDao {
    Optional<List<LanguageImpl>> getLanguages();
}
