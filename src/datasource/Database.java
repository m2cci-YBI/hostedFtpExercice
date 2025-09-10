package datasource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Database {
    private Database() {}
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    private static final DataSource JNDI_DS = lookupJndi();

    private static DataSource lookupJndi() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PureServletMVC");
            LOGGER.log(Level.INFO, "JNDI DataSource found: jdbc/PureServletMVC");
            return ds;
        } catch (NamingException e) {
            throw new RuntimeException("JNDI DataSource 'jdbc/PureServletMVC' not found. Ensure META-INF/context.xml or Tomcat context defines it and the driver is in TOMCAT_HOME/lib.", e);
        }
    }

    public static Connection get() throws SQLException {
        return JNDI_DS.getConnection();
    }
}
