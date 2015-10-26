package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private String host;
    private String user;
    private String pass;
    private String base;

    public DBConnection() {
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getBase() {
        return base;
    }

    public Connection getConnect() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String connectString = "jdbc:postgresql://" + host + "/" + base;
        Connection connect = DriverManager.getConnection(
                connectString,
                user,
                pass
        );
        connect.setAutoCommit(false);
        return connect;
    }
}
