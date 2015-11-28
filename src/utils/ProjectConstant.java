package utils;

import controller.Manager;

import java.awt.*;

/**
 * Created by luiz on 26/11/15.
 */
public class ProjectConstant {
    private static Manager manager;
    private static Font font;

    static {
        manager = new Manager();
        font = new Font("Tahoma", Font.PLAIN, 12);
    }

    public static Manager getManager() {
        return manager;
    }

    public static Font getFont() {
        return font;
    }
}
