package db.manipulate;


import exception.InvolvedException;
import model.Issue;
import model.Software;
import model.User;
import model.enums.IssueCategory;
import model.enums.IssueStatus;
import utils.ProjectConstant;

import java.sql.*;
import java.util.ArrayList;

public class IssueDAO {
    public static Issue create(Issue newIssue)
            throws SQLException, ClassNotFoundException {
        String sql =
                "INSERT INTO issue " +
                "(nm_issue, de_issue, dt_deadline, nm_requester, fl_category, cd_software)" +
                "VALUES(?, ?, ?, ?, ?, ?) " +
                "RETURNING cd_issue, dt_create;";
        Connection connect = ProjectConstant.getConnector().getConnect();
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

    public static Issue update(Issue toUpdate)
            throws SQLException, ClassNotFoundException {
        String sql =
                "UPDATE issues SET " +
                "nm_issue=?, de_issue=?, dt_start=?, dt_deadline=?, dt_over=?" +
                "WHERE cd_issue=?" +
                "RETURNING *";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        boolean updated = result.next();
        if (updated){
            toUpdate.setName(result.getString("nm_issue"));
            toUpdate.setDescription(result.getString("de_issue"));
            toUpdate.setStatus(IssueStatus.getStatus(result.getInt("fl_status")));
            toUpdate.setCreate(result.getDate("dt_create"));
            toUpdate.setStart(result.getDate("dt_start"));
            toUpdate.setDeadline(result.getDate("dt_deadline"));
            toUpdate.setOver(result.getDate("dt_over"));
            toUpdate.setCategory(IssueCategory.getCategory(result.getInt("fl_category")));
        }
        result.close();
        stmt.close();
        connect.commit();
        connect.close();
        return updated? toUpdate : null;
    }

    public static Issue becomeInvolved(Issue issue, User involved, boolean toApprove)
            throws SQLException, ClassNotFoundException, InvolvedException {
        String sql = "UPDATE issues SET ";
        sql += toApprove? "nm_approving=? ": "nm_maker=? dt_start=current_date ";
        sql += "WHERE cd_issue=? RETURNING dt_start;";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, involved.getLogin());
        stmt.setLong(2, issue.getUuid());
        ResultSet result = stmt.executeQuery();
        if (result.next()){
            if (toApprove) issue.setApproving(involved);
            else{
                issue.setMaker(involved);
                issue.setStart(result.getDate("dt_start"));
            }
        } else throw new InvolvedException(toApprove? "Ocorreu um problema ao aprovar" : "Ocorreu um problema ao assumir");
        result.close();
        stmt.close();
        connect.commit();
        connect.close();
        return issue;
    }

    public static ArrayList<Issue> list() throws SQLException, ClassNotFoundException {
        ArrayList<Issue> issues = new ArrayList<>();
        String sql =
                "SELECT i.*, nm_software, nu_release, nu_minor, nu_fix " +
                "FROM issues i " +
                "LEFT JOIN software s ON s.cd_software = i.cd_software " +
                "WHERE fl_deprecate=FALSE;";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        while (result.next()){
            Issue issue = new Issue();
            Long id = result.getLong("cd_issue");
            issue.setUuid(id);
            issue.setName(result.getString("nm_issue"));
            issue.setDescription(result.getString("de_issue"));
            issue.setStatus(IssueStatus.getStatus(result.getInt("fl_status")));
            issue.setCreate(result.getDate("dt_create"));
            issue.setStart(result.getDate("dt_start"));
            issue.setDeadline(result.getDate("dt_deadline"));
            issue.setOver(result.getDate("dt_over"));
            issue.setCategory(IssueCategory.getCategory(result.getInt("fl_category")));

            Software software = new Software();
            software.setUuid(result.getLong("cd_software"));
            software.setName(result.getString("nm_software"));
            software.setReleaseVersion(result.getInt("nu_release"));
            software.setMinorVersion(result.getInt("nu_minor"));
            software.setFixVersion(result.getInt("nu_fix"));
            software.setDeprecate(false);
            issue.setSoftware(software);

            issue.setRequester(UserDAO.find(result.getString("nm_requester")));
            issue.setApproving(UserDAO.find(result.getString("nm_approving")));
            issue.setMaker(UserDAO.find(result.getString("nm_maker")));

            issue.setComments(CommentDAO.list(id));

            issues.add(issue);
        }
        return issues;
    }

    public static ArrayList<Issue> list(User currentUser) throws SQLException, ClassNotFoundException{
        ArrayList<Issue> issues = new ArrayList<>();
        String sql =
                "SELECT i.*, nm_software, nu_release, nu_minor, nu_fix, fl_deprecate " +
                "FROM issues i " +
                "LEFT JOIN software s ON s.cd_software = i.cd_software " +
                "WHERE fl_deprecate=FALSE AND (i.nm_maker=? OR i.nm_requester=? OR i.nm_approving=?);";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, currentUser.getLogin());
        stmt.setString(2, currentUser.getLogin());
        stmt.setString(3, currentUser.getLogin());
        ResultSet result = stmt.executeQuery();
        while (result.next()){
            Issue issue = new Issue();
            Long id = result.getLong("cd_issue");
            issue.setUuid(id);
            issue.setName(result.getString("nm_issue"));
            issue.setDescription(result.getString("de_issue"));
            issue.setStatus(IssueStatus.getStatus(result.getInt("fl_status")));
            issue.setCreate(result.getDate("dt_create"));
            issue.setStart(result.getDate("dt_start"));
            issue.setDeadline(result.getDate("dt_deadline"));
            issue.setOver(result.getDate("dt_over"));
            issue.setCategory(IssueCategory.getCategory(result.getInt("fl_category")));

            Software software = new Software();
            software.setUuid(result.getLong("cd_software"));
            software.setName(result.getString("nm_software"));
            software.setReleaseVersion(result.getInt("nu_release"));
            software.setMinorVersion(result.getInt("nu_minor"));
            software.setFixVersion(result.getInt("nu_fix"));
            software.setDeprecate(false);
            issue.setSoftware(software);

            issue.setRequester(UserDAO.find(result.getString("nm_requester")));
            issue.setApproving(UserDAO.find(result.getString("nm_approving")));
            issue.setMaker(UserDAO.find(result.getString("nm_maker")));

            issue.setComments(CommentDAO.list(id));

            issues.add(issue);
        }
        result.close();
        stmt.close();
        connect.close();
        return issues;
    }

    public static int count(int softwareId)
            throws SQLException, ClassNotFoundException {
        String sql =
                "SELECT count(cd_software) " +
                "FROM issues " +
                "WHERE cd_software=? AND fl_status IN(3, 4);";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setInt(1, softwareId);
        ResultSet result = stmt.executeQuery();
        result.next();
        int count = result.getInt("count");
        result.close();
        stmt.close();
        connect.close();
        return count;
    }
}
