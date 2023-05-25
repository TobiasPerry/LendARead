package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;
import ar.itba.edu.paw.persistenceinterfaces.LanguageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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
    public List<LanguageImpl> getLanguages() {
        Optional<List<LanguageImpl>> langsOpt = this.languageDao.getLanguages();
        if (!langsOpt.isPresent()) {
            LOGGER.error("Couldn't load languages");
            throw new RuntimeException("Couldn't load languages");
        }

        List<LanguageImpl> languages = langsOpt.get();
        languages.sort(Comparator.comparing(LanguageImpl::getName));
        return languages;
    }
}
