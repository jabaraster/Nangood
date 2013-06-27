package jp.co.city.nangood;

import jabara.general.ExceptionUtil;
import jabara.jetty.ServerStarter;
import jabara.jpa.util.SystemPropertyToPostgreJpaPropertiesParser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.eclipse.jetty.plus.jndi.Resource;
import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.ds.PGPoolingDataSource;

/**
 * 
 */
public class WebStarter {
    /**
     * @throws NamingException -
     */
    @SuppressWarnings({ "unused", "nls" })
    public static void initializeDataSource() throws NamingException {
        new Resource("jdbc/" + Environment.getApplicationName(), createDataSource());
    }

    /**
     * @param pArgs 起動引数.
     * @throws NamingException -
     */
    public static void main(final String[] pArgs) throws NamingException {
        initializeDataSource();
        final ServerStarter server = new ServerStarter();
        server.start();
        // MemcachedをHttpSessionのストアとして使う場合.
        // 主にHerokuでの運用を意識.
        // new MemcachedSessionServerStarter().start();
    }

    private static DataSource createDataSource() {
        final Map<String, String> properties = new SystemPropertyToPostgreJpaPropertiesParser().produce();
        if (properties.isEmpty()) {
            return createH2Database();
        }
        return createPostgreSQLDataBaseFromHeroku();
    }

    private static DataSource createH2Database() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:target/db/db"); //$NON-NLS-1$
        dataSource.setUser("sa"); //$NON-NLS-1$
        return dataSource;
    }

    private static DataSource createPostgreSQLDataBaseFromHeroku() {
        final String databaseUrl = System.getProperty(SystemPropertyToPostgreJpaPropertiesParser.KEY_DATABASE_URL);
        jabara.Debug.write(databaseUrl);
        try {
            final URI dbUri = new URI(databaseUrl);

            final String userInfo = dbUri.getUserInfo();
            final Credential credential = parseCredential(userInfo);

            final String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath() + ":" + dbUri.getPort(); //$NON-NLS-1$ //$NON-NLS-2$
            jabara.Debug.write(dbUri);

            final PGPoolingDataSource dataSource = new PGPoolingDataSource();
            dataSource.setServerName(dbUri.getHost());
            dataSource.setPortNumber(dbUri.getPort());
            dataSource.setUser(credential.getUserName());
            dataSource.setPassword(credential.getPassword());

            return dataSource;

        } catch (final URISyntaxException e) {
            throw ExceptionUtil.rethrow(e);
        }

    }

    @SuppressWarnings("synthetic-access")
    private static Credential parseCredential(final String pUserInfo) {
        if (pUserInfo == null) {
            return Credential.ANONYMOUS;
        }
        final String[] tokens = pUserInfo.split(":"); //$NON-NLS-1$
        if (tokens.length == 0) {
            return Credential.ANONYMOUS;
        }

        final String userName;
        final String password;
        if (tokens.length < 2) {
            userName = tokens[0];
            password = ""; //$NON-NLS-1$
        } else {
            userName = tokens[0];
            password = tokens[1];
        }
        return new Credential(userName, password);
    }

    private static final class Credential {
        private static final Credential ANONYMOUS = new Credential("", ""); //$NON-NLS-1$//$NON-NLS-2$

        private final String            userName;
        private final String            password;

        Credential(final String pUserName, final String pPassword) {
            this.userName = pUserName;
            this.password = pPassword;
        }

        String getPassword() {
            return this.password;
        }

        String getUserName() {
            return this.userName;
        }

    }
}
