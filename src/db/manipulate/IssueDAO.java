package db.manipulate;


import model.Issue;

import java.sql.*;
import java.util.List;

public class IssueDAO {
    public static Issue create(Issue newIssue, Connection connect)
            throws SQLException {
        String sql =
                "INSERT INTO issue " +
                "(nm_issue, de_issue, dt_deadline, nm_requester, fl_category, cd_software)" +
                "VALUES(?, ?, ?, ?, ?, ?) " +
                "RETURNING cd_issue, dt_create;";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, newIssue.getName());
        stmt.setString(2, newIssue.getDescription());
        stmt.setDate(3, new Date(newIssue.getDeadline().getTime()));
        stmt.setString(4, newIssue.getRequester().getLogin());
        stmt.setInt(5, newIssue.getCategory().ordinal());
        stmt.setLong(6, newIssue.getSoftware().getUuid());
        try {
            ResultSet result = stmt.executeQuery();
            result.next();
            newIssue.setCreate(result.getDate("dt_create"));
            newIssue.setUuid(result.getLong("cd_issue"));
            result.close();
            stmt.close();
            connect.commit();
            connect.close();
            return newIssue;
        } catch (SQLException e){
            stmt.close();
            connect.close();
            return null;
        }
    }

    public static boolean update(Issue toUpdate, Connection connect){
        return true;
    }

    public static List<Issue> list(int start, int limit, Connection connection){
        return null;
    }

}
