/**
 * 
 */
package jp.co.city.nangood.service;

import jabara.general.NotFound;

import java.util.List;

import jp.co.city.nangood.entity.ESession;
import jp.co.city.nangood.model.Duplicate;
import jp.co.city.nangood.service.impl.SessionServiceImpl;

import com.google.inject.ImplementedBy;

/**
 * @author jabaraster
 */
@ImplementedBy(SessionServiceImpl.class)
public interface ISessionService {

    /**
     * @param pSession -
     */
    void delete(ESession pSession);

    /**
     * @param pEnglishName -
     * @return -
     * @throws NotFound -
     */
    ESession findByEnglishName(String pEnglishName) throws NotFound;

    /**
     * @param pId -
     * @return -
     * @throws NotFound -
     */
    ESession findById(long pId) throws NotFound;

    /**
     * @return -
     */
    List<ESession> getAll();

    /**
     * @param pSession -
     * @throws Duplicate セッション英名が重複していた場合.
     */
    void save(ESession pSession) throws Duplicate;
}
