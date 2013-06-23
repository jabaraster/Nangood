/**
 * 
 */
package jp.co.city.nangood.service;

import jabara.general.NotFound;

import java.util.List;

import jp.co.city.nangood.entity.ESession;
import jp.co.city.nangood.service.impl.SessionServiceImpl;

import com.google.inject.ImplementedBy;

/**
 * @author jabaraster
 */
@ImplementedBy(SessionServiceImpl.class)
public interface ISessionService {

    /**
     * @param pEnglishName -
     * @return -
     * @throws NotFound -
     */
    ESession findByEnglishName(String pEnglishName) throws NotFound;

    /**
     * @return -
     */
    List<ESession> getAll();
}
