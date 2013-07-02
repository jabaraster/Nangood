/**
 * 
 */
package jp.co.city.nangood.web.rest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.core.MediaType;

import jp.co.city.nangood.WebStarter;
import jp.co.city.nangood.web.rest.NangoodResource.NangoodEntity;
import net.arnx.jsonic.JSON;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.Client;

/**
 * @author jabaraster
 */
public class NangoodResourceTest {

    /**
     * 
     */
    @SuppressWarnings({ "nls", "static-method" })
    @Test
    public void _post() {
        final List<NangoodEntity> list = Arrays.asList( //
                new NangoodEntity(new Date(), 5) //
                );
        final String json = JSON.encode(list);
        final Client client = Client.create();
        client.resource("http://localhost:8081/rest/nangood/cancer") //
                .accept(MediaType.APPLICATION_JSON) //
                .type(MediaType.APPLICATION_JSON) //
                .post(json) //
        ;
    }

    /**
     * @throws NamingException -
     */
    @BeforeClass
    public static void beforeClass() throws NamingException {
        WebStarter.initializeDataSource();
    }

}
