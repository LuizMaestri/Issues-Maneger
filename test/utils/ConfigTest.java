package utils;

import db.DBConnection;
import junit.framework.TestCase;

public class ConfigTest extends TestCase {

    public void testGetDBConfig(){
        Config.setPathFile("config/configtest/db_config_test.xml");
        DBConnection test = Config.getDBConfig();
        assertEquals("localhost:5432", test.getHost());
        assertEquals("luiz", test.getUser());
        assertEquals("r1c4rd0*", test.getPass());
        assertEquals("APS", test.getBase());
    }

}