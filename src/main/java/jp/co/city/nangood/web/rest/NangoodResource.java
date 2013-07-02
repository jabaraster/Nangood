/**
 * 
 */
package jp.co.city.nangood.web.rest;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * @author jabaraster
 */
@Path("nangood")
public class NangoodResource {

    /**
     * 
     */
    public static final String PARAM_SESSION_ID = "sessionId"; //$NON-NLS-1$

    /**
     * @param pSessionId -
     * @param pHeaders -
     * @param pNangoods -
     */
    @SuppressWarnings("static-method")
    @Path("{" + PARAM_SESSION_ID + "}")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
    public void post( //
            @PathParam(PARAM_SESSION_ID) final String pSessionId //
            , @Context final HttpHeaders pHeaders //
            , final NangoodEntityList pNangoods //
    ) {
        System.out.println(pNangoods);
    }

    /**
     * @author jabaraster
     */
    public static class NangoodEntity {
        /**
         * 
         */
        public Date time;
        /**
         * 
         */
        public int  count;

        /**
         * 
         */
        public NangoodEntity() {
            // 処理なし
        }

        /**
         * @param pTime
         * @param pCount
         */
        public NangoodEntity(final Date pTime, final int pCount) {
            this.time = pTime;
            this.count = pCount;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @SuppressWarnings("nls")
        @Override
        public String toString() {
            return "NangoodEntity [time=" + this.time + ", count=" + this.count + "]";
        }
    }

    private static class NangoodEntityList extends ArrayList<NangoodEntity> {
        private static final long serialVersionUID = 796570809371692069L;
    }
}
