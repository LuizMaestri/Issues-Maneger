package db.manipulate;


import exception.AuthenticationUserException;
import java.sql.SQLException;
import junit.framework.TestCase;
import model.User;
import utils.Config;

public class UserDAOTest extends TestCase {
    public void setUp() {
        Config.setPathFile("config/test/db_config_test.xml");
    }

    public void testCreateError() {
        User testUser = new User();
        testUser.setLogin("admin");
        testUser.setPass("admin");
        testUser.setName("admin");

        try {
            assertFalse(
                    UserDAO.create(
                            testUser,
                            Config.getDBConfig().getConnect()
                    )
            );
        } catch (ClassNotFoundException | SQLException e) {
            fail(e.getMessage());
        }

    }

    public void testCreateSuccess() {
        User testUser = new User();
        testUser.setLogin("luiz");
        testUser.setPass("1234");
        testUser.setName("Luiz Maestri");

        try {
            assertTrue(
                    UserDAO.create(
                            testUser,
                            Config.getDBConfig().getConnect()
                    )
            );
        } catch (ClassNotFoundException | SQLException e) {
            fail(e.getMessage());
        }

    }

    public void testAuthFail() {
        try {
            UserDAO.auth(
                    new User(),
                    Config.getDBConfig().getConnect()
            );
        } catch (ClassNotFoundException | SQLException | AuthenticationUserException e) {
            assertSame(AuthenticationUserException.class, e.getClass());
        }
    }

    public void testAuthSuccess() {
        User testUser = new User();
        testUser.setLogin("admin");
        testUser.setPass("admin");
        try {
            assertNotNull(
                    UserDAO.auth(
                            testUser,
                            Config.getDBConfig().getConnect()
                    )
            );
        } catch (ClassNotFoundException | SQLException | AuthenticationUserException e) {
            fail(e.getMessage());
        }
    }

    public void testUpdate() {
        User testUser = new User();
        testUser.setLogin("luiz");
        testUser.setPass("1234");
        testUser.setName("Luiz Ricardo Maestri");
        try {
            assertTrue(
                    UserDAO.update(
                            testUser,
                            Config.getDBConfig().getConnect()
                    )
            );
        } catch (SQLException | ClassNotFoundException e) {
            fail(e.getMessage());
        }
    }

    public void testDelete() {
        try {
            assertTrue(
                    UserDAO.delete(
                            "luiz",
                            Config.getDBConfig().getConnect()
                    )
            );
        } catch (SQLException | ClassNotFoundException e) {
            fail(e.getMessage());
        }
    }
}
