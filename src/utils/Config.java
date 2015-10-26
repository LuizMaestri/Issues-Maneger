package utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import db.DBConnection;
import java.io.File;

public class Config {

    private static String pathFile = "config/db_config.xml";

    public static DBConnection getDBConfig(){
        XStream stream = new XStream(new DomDriver());
        stream.alias("DBConnect", DBConnection.class);
        return (DBConnection) stream.fromXML(new File(pathFile));
    }

    public static void setPathFile(String newPath){
        pathFile = newPath;
    }
}
