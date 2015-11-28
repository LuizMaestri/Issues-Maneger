package db.manipulate;

import model.Software;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by luiz on 25/11/15.
 */
public class SoftwareDAO {
    public static boolean create(Software newSoftware, Connection connect)
            throws SQLException {
        String sql =
                "INSERT INTO software " +
                "(nm_software, cd_release, cd_minor, cd_fix) " +
                "VALUES (?, ?, ?, ?)" +
                "RETURNING cd_software;";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, newSoftware.getName());
        stmt.setInt(2, newSoftware.getReleaseVersion());
        stmt.setInt(3, newSoftware.getMinorVersion());
        stmt.setInt(4, newSoftware.getFixVersion());
        try {
            stmt.executeQuery();
            stmt.close();
            connect.commit();
            connect.close();
            return true;
        } catch (SQLException e){
            stmt.close();
            connect.close();
            return false;
        }
    }

    public static boolean update(Software toUpdate, Connection connect) throws SQLException {
        String sql =
                "UPDATE software " +
                "SET nm_software=?, cd_release=?, cd_minor=?, cd_fix=?" +
                "WHERE cd_software=?" +
                "RETURNING cd_software;";
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
        return updated;
    }

    public static Software search(long id, Connection connect)
            throws SQLException {
        Software software = null;
        String sql =
                "SELECT * FROM software " +
                "WHERE cd_software=?;";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet result = stmt.executeQuery();
        if (result.next()){
            software = new Software();
            software.setUuid(id);
            software.setName(result.getString("nm_software"));
            software.setReleaseVersion(result.getInt("cd_release"));
            software.setMinorVersion(result.getInt("cd_minor"));
            software.setFixVersion(result.getInt("cd_fix"));
        }
        result.close();
        stmt.close();
        connect.close();
        return software;
    }

    public static boolean deprecate(long id, Connection connect) throws SQLException {
        String sql =
                "UPDATE software " +
                "SET fl_deprecate=true " +
                "WHERE cd_software=?;";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setLong(1, id);
        boolean updated = stmt.executeUpdate() != 0;
        stmt.close();
        connect.commit();
        connect.close();
        return updated;

    }
}
