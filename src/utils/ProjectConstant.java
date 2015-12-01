package utils;

import controller.Manager;
import db.DBConnection;

import java.awt.*;

/**
 * Created by luiz on 26/11/15.
 */
public class ProjectConstant {
    private static Manager manager;
    private static Font font;
    private static DBConnection connector;

    static {
        manager = new Manager();
        font = new Font("Tahoma", Font.PLAIN, 12);
        connector = Config.getDBConfig();
    }

    public static Manager getManager() {
        return manager;
    }

    public static Font getFont() {
        return font;
    }

    public static DBConnection getConnector() {
        return connector;
    }
}
