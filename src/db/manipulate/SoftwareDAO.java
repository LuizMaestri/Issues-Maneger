package db.manipulate;

import model.Software;
import utils.ProjectConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by luiz on 25/11/15.
 */
public class SoftwareDAO {
    public static Software create(Software newSoftware)
            throws SQLException, ClassNotFoundException {
        String sql =
                "INSERT INTO software " +
                "(nm_software, nu_release, nu_minor, nu_fix) " +
                "VALUES (?, ?, ?, ?)" +
                "RETURNING cd_software;";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, newSoftware.getName());
        stmt.setInt(2, newSoftware.getReleaseVersion());
        stmt.setInt(3, newSoftware.getMinorVersion());
        stmt.setInt(4, newSoftware.getFixVersion());
        try {
            ResultSet result = stmt.executeQuery();
            if(result.next()) newSoftware.setUuid(result.getLong("cd_software"));
            result.close();
            stmt.close();
            connect.commit();
            connect.close();
            return newSoftware;
        } catch (SQLException e){
            stmt.close();
            connect.close();
            return null;
        }
    }

    public static Software update(Software toUpdate)
            throws SQLException, ClassNotFoundException {
        String sql =
                "UPDATE software " +
                "SET nm_software=?, nu_release=?, nu_minor=?, nu_fix=? " +
                "WHERE cd_software=?" +
                "RETURNING cd_software;";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, toUpdate.getName());
        stmt.setInt(2, toUpdate.getReleaseVersion());
        stmt.setInt(3, toUpdate.getMinorVersion());
        stmt.setInt(4, toUpdate.getFixVersion());
        stmt.setLong(5, toUpdate.getUuid());
        ResultSet result = stmt.executeQuery();
        boolean updated = result.next();
        result.close();
        stmt.close();
        connect.commit();
        connect.close();
        return updated? toUpdate : null;
    }

    public static Software search(long id)
            throws SQLException, ClassNotFoundException {
        Software software = null;
        String sql =
                "SELECT * FROM software " +
                "WHERE cd_software=?;";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet result = stmt.executeQuery();
        if (result.next()){
            software = new Software();
            software.setUuid(id);
            software.setName(result.getString("nm_software"));
            software.setReleaseVersion(result.getInt("nu_release"));
            software.setMinorVersion(result.getInt("nu_minor"));
            software.setFixVersion(result.getInt("nu_fix"));
            software.setDeprecate(result.getBoolean("fl_deprecate"));
        }
        result.close();
        stmt.close();
        connect.close();
        return software;
    }

    public static ArrayList<Software> list()
            throws SQLException, ClassNotFoundException {
        ArrayList<Software> softwares = new ArrayList<>();
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement("SELECT * FROM software WHERE fl_deprecate=FALSE;");
        ResultSet result = stmt.executeQuery();
        while (result.next()){
            Software software = new Software();
            software.setUuid(result.getLong("cd_software"));
            software.setName(result.getString("nm_software"));
            software.setReleaseVersion(result.getInt("nu_release"));
            software.setMinorVersion(result.getInt("nu_minor"));
            software.setFixVersion(result.getInt("nu_fix"));
            software.setDeprecate(false);
            softwares.add(software);
        }
        return softwares;
    }

    public static boolean deprecate(long id) throws SQLException, ClassNotFoundException {
        String sql =
                "UPDATE software " +
                "SET fl_deprecate=true " +
                "WHERE cd_software=?;";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setLong(1, id);
        boolean updated = stmt.executeUpdate() != 0;
        stmt.close();
        connect.commit();
        connect.close();
        return updated;

    }
}
