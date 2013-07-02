/**
 * 
 */
package jp.co.city.nangood.entity;

import jabara.general.ArgUtil;
import jabara.jpa.entity.EntityBase;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author jabaraster
 */
@Entity
public class ENangood extends EntityBase<ENangood> {
    private static final long serialVersionUID = -6143925433653998609L;

    /**
     * 
     */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date            time;

    /**
     * 
     */
    @Column(nullable = false)
    protected int             count;

    /**
     * 
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    protected ESession        session;

    /**
     * 
     */
    public ENangood() {
        // 処理なし
    }

    /**
     * @param pSession -
     * @param pTime -
     * @param pCount -
     */
    public ENangood(final ESession pSession, final Date pTime, final int pCount) {
        ArgUtil.checkNull(pSession, "pSession"); //$NON-NLS-1$
        ArgUtil.checkNull(pTime, "pTime"); //$NON-NLS-1$
        checkCount(pCount);
        this.time = pTime;
        this.count = pCount;
        this.session = pSession;
    }

    /**
     * @return countを返す.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * @return sessionを返す.
     */
    public ESession getSession() {
        return this.session;
    }

    /**
     * @return timeを返す.
     */
    public Date getTime() {
        return this.time == null ? null : new Date(this.time.getTime());
    }

    /**
     * @param pCount countを設定.
     */
    public void setCount(final int pCount) {
        this.count = pCount;
    }

    /**
     * @param pSession sessionを設定.
     */
    public void setSession(final ESession pSession) {
        this.session = pSession;
    }

    /**
     * @param pTime timeを設定.
     */
    public void setTime(final Date pTime) {
        this.time = pTime == null ? null : new Date(pTime.getTime());
    }

    private static void checkCount(final int pCount) {
        if (pCount < 0) {
            throw new IllegalStateException();
        }
    }

}
