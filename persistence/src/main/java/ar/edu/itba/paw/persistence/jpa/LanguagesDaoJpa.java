package ar.edu.itba.paw.persistence.jpa;

import ar.edu.itba.paw.models.assetExistanceContext.implementations.LanguageImpl;
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
    public Optional<List<LanguageImpl>> getLanguages() {
        TypedQuery<LanguageImpl> query = em.createQuery("from LanguageImpl", LanguageImpl.class);
        List<LanguageImpl> languages = query.getResultList();
        return Optional.ofNullable(languages);
    }
}
