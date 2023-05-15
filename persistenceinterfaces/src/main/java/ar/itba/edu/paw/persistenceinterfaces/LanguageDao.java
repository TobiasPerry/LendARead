package ar.itba.edu.paw.persistenceinterfaces;

import java.util.HashMap;
import java.util.Optional;

public interface LanguageDao {
    Optional<HashMap<String, String>> getLanguages();
}
