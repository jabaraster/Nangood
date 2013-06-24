/**
 * 
 */
package jp.co.city.nangood.entity;

import jabara.bean.annotation.Hidden;
import jabara.bean.annotation.Order;
import jabara.jpa.entity.EntityBase;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * @param <E> -
 * @author jabaraster
 */
@MappedSuperclass
public abstract class AppEntity<E extends AppEntity<E>> extends EntityBase<E> {
    private static final long serialVersionUID = -1975896691881771778L;

    /**
     * @see jabara.jpa.entity.EntityBase#getCreated()
     */
    @Override
    @Hidden
    public Date getCreated() {
        return super.getCreated();
    }

    /**
     * @see jabara.jpa.entity.EntityBase#getId()
     */
    @Override
    @Order(Integer.MIN_VALUE)
    public Long getId() {
        return super.getId();
    }

    /**
     * @see jabara.jpa.entity.EntityBase#getUpdated()
     */
    @Override
    @Hidden
    public Date getUpdated() {
        return super.getUpdated();
    }

    /**
     * @see jabara.jpa.entity.EntityBase#isPersisted()
     */
    @Override
    @Hidden
    public boolean isPersisted() {
        return super.isPersisted();
    }
}
