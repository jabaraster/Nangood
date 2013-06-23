/**
 * 
 */
package jp.co.city.nangood.service.impl;

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
     * @see jp.co.city.nangood.service.ISessionService#findByEnglishName(java.lang.String)
     */
    @Override
    public ESession findByEnglishName(final String pEnglishName) throws NotFound {
        final EntityManager em = getEntityManager();
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<ESession> query = builder.createQuery(ESession.class);
        final Root<ESession> root = query.from(ESession.class);

        query.where(builder.equal(root.get(ESession_.englishName), pEnglishName));

        return getSingleResult(em.createQuery(query));
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

}
