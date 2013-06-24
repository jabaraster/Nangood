/**
 * 
 */
package jp.co.city.nangood.service.impl;

import jabara.general.ArgUtil;
import jabara.general.NotFound;
import jabara.jpa.JpaDaoBase;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import jp.co.city.nangood.entity.ESession;
import jp.co.city.nangood.entity.ESession_;
import jp.co.city.nangood.model.Duplicate;
import jp.co.city.nangood.service.ISessionService;

/**
 * @author jabaraster
 */
public class SessionServiceImpl extends JpaDaoBase implements ISessionService {
    private static final long serialVersionUID = 1493914594921040289L;

    /**
     * @param pEntityManagerFactory -
     */
    @Inject
    public SessionServiceImpl(final EntityManagerFactory pEntityManagerFactory) {
        super(pEntityManagerFactory);
    }

    /**
     * @see jp.co.city.nangood.service.ISessionService#delete(jp.co.city.nangood.entity.ESession)
     */
    @Override
    public void delete(final ESession pSession) {
        ArgUtil.checkNull(pSession, "pSession"); //$NON-NLS-1$

        final EntityManager em = getEntityManager();
        if (em.contains(pSession)) {
            em.remove(pSession);
        } else {
            em.remove(em.merge(pSession));
        }
    }

    /**
     * @see jp.co.city.nangood.service.ISessionService#findByEnglishName(java.lang.String)
     */
    @Override
    public ESession findByEnglishName(final String pEnglishName) throws NotFound {
        return findByEnglishNameCore(pEnglishName);
    }

    /**
     * @see jp.co.city.nangood.service.ISessionService#findById(long)
     */
    @Override
    public ESession findById(final long pId) throws NotFound {
        final EntityManager em = getEntityManager();
        final ESession ret = em.find(ESession.class, Long.valueOf(pId));
        if (ret == null) {
            throw NotFound.GLOBAL;
        }
        return ret;
    }

    /**
     * @see jp.co.city.nangood.service.ISessionService#getAll()
     */
    @Override
    public List<ESession> getAll() {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<ESession> query = builder.createQuery(ESession.class);
        final Root<ESession> root = query.from(ESession.class);
        query.orderBy(builder.asc(root.get(ESession_.englishName)));
        return em.createQuery(query).getResultList();
    }

    /**
     * @see jp.co.city.nangood.service.ISessionService#save(jp.co.city.nangood.entity.ESession)
     */
    @Override
    public void save(final ESession pSession) throws Duplicate {
        ArgUtil.checkNull(pSession, "pSession"); //$NON-NLS-1$
        if (pSession.isPersisted()) {
            updateCore(pSession);
        } else {
            insertCore(pSession);
        }
    }

    private boolean englishNameIsDuplicate(final String pEnglishName) {
        try {
            findByEnglishName(pEnglishName);
            return true;
        } catch (final NotFound e) {
            return false;
        }
    }

    private ESession findByEnglishNameCore(final String pEnglishName) throws NotFound {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<ESession> query = builder.createQuery(ESession.class);
        final Root<ESession> root = query.from(ESession.class);

        query.where(builder.equal(root.get(ESession_.englishName), pEnglishName));

        return getSingleResult(em.createQuery(query));
    }

    private void insertCore(final ESession pSession) throws Duplicate {
        if (englishNameIsDuplicate(pSession.getEnglishName())) {
            throw Duplicate.INSTANCE;
        }
        getEntityManager().persist(pSession);
    }

    private void updateCore(final ESession pSession) {
        final EntityManager em = getEntityManager();
        if (em.contains(pSession)) {
            return;
        }
        final ESession merged = em.merge(pSession);
        merged.setDescription(pSession.getDescription());
        merged.setEnglishName(pSession.getEnglishName());
        merged.setName(pSession.getName());
    }

}
