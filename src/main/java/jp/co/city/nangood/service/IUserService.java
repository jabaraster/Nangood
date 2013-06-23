package jp.co.city.nangood.service;

import jabara.general.Sort;

import java.util.List;

import jp.co.city.nangood.entity.EUser;
import jp.co.city.nangood.service.impl.UserServiceImpl;

import com.google.inject.ImplementedBy;

/**
 * 
 */
@ImplementedBy(UserServiceImpl.class)
public interface IUserService {

    /**
     * @param pSort ソート条件.
     * @return 全件.
     */
    List<EUser> getAll(Sort pSort);

    /**
     * 
     */
    void insertAdministratorIfNotExists();
}
