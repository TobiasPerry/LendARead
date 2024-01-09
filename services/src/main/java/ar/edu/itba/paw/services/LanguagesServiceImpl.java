package ar.edu.itba.paw.services;

import ar.edu.itba.paw.exceptions.LanguageNotFoundException;
import ar.edu.itba.paw.interfaces.LanguagesService;
import ar.edu.itba.paw.models.assetExistanceContext.implementations.Language;
import ar.edu.itba.paw.models.viewsContext.interfaces.AbstractPage;
import ar.edu.itba.paw.utils.HttpStatusCodes;
import ar.itba.edu.paw.persistenceinterfaces.LanguageDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LanguagesServiceImpl implements LanguagesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguagesServiceImpl.class);
    private final LanguageDao languageDao;

    @Autowired
    public LanguagesServiceImpl(LanguageDao langDao) {
        languageDao = langDao;
    }

    @Transactional(readOnly = true)
    @Override
    public AbstractPage<Language> getLanguages(final int page, final int itemsPerPage, final Boolean isUsed) {
      return this.languageDao.getLanguages(page, itemsPerPage, isUsed);


    }
    @Transactional(readOnly = true)
    @Override
    public Language getLanguage(String code) throws LanguageNotFoundException {
       Optional<Language> langOpt = this.languageDao.getLanguage(code);
       LOGGER.debug("Language with code {} ", code);
       langOpt.orElseThrow(() -> new LanguageNotFoundException(HttpStatusCodes.NOT_FOUND));
       return langOpt.get();
    }
}
