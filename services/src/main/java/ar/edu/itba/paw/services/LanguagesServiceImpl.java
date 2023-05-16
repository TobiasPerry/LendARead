package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.itba.edu.paw.persistenceinterfaces.LanguageDao;
import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class LanguagesServiceImpl implements LanguagesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);
    private final LanguageDao languageDao;

    @Autowired
    public LanguagesServiceImpl(LanguageDao langDao) {
        languageDao = langDao;
    }

    @Override
    public HashMap<String, String> getLanguages() {
        Optional<HashMap<String, String>> langsOpt = this.languageDao.getLanguages();
        if (!langsOpt.isPresent()) {
            LOGGER.error("Couldn't load languages");
            throw new RuntimeException("Couldn't load languages");
        }
        HashMap<String, String> languages = langsOpt.get();
        return languages;
    }
}
