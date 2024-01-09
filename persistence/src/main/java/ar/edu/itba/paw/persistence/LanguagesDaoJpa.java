package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.Language;
import ar.itba.edu.paw.persistenceinterfaces.LanguageDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class LanguagesDaoJpa implements LanguageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<List<Language>> getLanguages() {
        TypedQuery<Language> query = em.createQuery("from Language", Language.class);
        List<Language> languages = query.getResultList();
        return Optional.ofNullable(languages);
    }
}
