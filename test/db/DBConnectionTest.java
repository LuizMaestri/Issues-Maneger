package db;

import junit.framework.TestCase;
import org.postgresql.util.PSQLException;
import utils.Config;
import java.sql.SQLException;

public class DBConnectionTest extends TestCase{
    public void testHostError() {
        Config.setPathFile("config/test/db_fail_host.xml");
        try {
            Config.getDBConfig().getConnect();
        } catch (SQLException | ClassNotFoundException e) {
            assertSame(PSQLException.class, e.getClass());
        }
    }

    public void testConnectError(){
        Config.setPathFile("config/test/db_fail_connect.xml");
        try {
            Config.getDBConfig().getConnect();
        } catch (SQLException | ClassNotFoundException e) {
            assertSame(PSQLException.class, e.getClass());
        }
    }

    public void testGetConnect(){
        Config.setPathFile("config/test/db_config_test.xml");
        try {
            assertNotNull(Config.getDBConfig().getConnect());
        } catch (SQLException | ClassNotFoundException e) {
            fail("DATABASE CONNECT ERROR!!");
        }
    }
}
