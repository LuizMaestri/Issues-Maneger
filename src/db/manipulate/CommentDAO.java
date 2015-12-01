package db.manipulate;

import model.Comment;
import utils.ProjectConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by luiz on 29/11/15.
 */
public class CommentDAO {
    public static Comment create(Comment newComment)
            throws SQLException, ClassNotFoundException {
        String sql =
                "INSERT INTO comment " +
                "(cd_issue, nm_user, de_comment) " +
                "VALUES (?, ?, ?) " +
                "RETURNING cd_comment, dt_comment;";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setLong(1, newComment.getIssueId());
        stmt.setString(2, newComment.getUser().getLogin());
        stmt.setString(3, newComment.getComment());
        ResultSet result = stmt.executeQuery();
        if (result.next()){
            newComment.setUuid(result.getLong("cd_comment"));
            newComment.setDate(result.getDate("dt_comment"));
        }
        result.close();
        stmt.close();
        connect.commit();
        connect.close();
        return newComment;
    }

    public static ArrayList<Comment> list(Long issueId)
            throws SQLException, ClassNotFoundException {
        ArrayList<Comment> comments = new ArrayList<>();
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement("SELECT * FROM comment WHERE cd_issue=?;");
        stmt.setLong(1, issueId);
        ResultSet result = stmt.executeQuery();
        while (result.next()){
            Comment comment = new Comment();
            comment.setUuid(result.getLong("cd_comment"));
            comment.setIssueId(result.getLong("cd_issue"));
            comment.setComment(result.getString("de_comment"));
            comment.setDate(result.getDate("dt_comment"));
            comment.setUser(UserDAO.find(result.getString("nm_user")));
            comments.add(comment);
        }
        result.close();
        stmt.close();
        connect.close();
        return comments;
    }

    public static boolean delete(Long commentId)
            throws SQLException, ClassNotFoundException {
        String sql =
                "DELETE FROM comment " +
                "WHERE cd_comment=? " +
                "RETURNING cd_comment;";
        Connection connect = ProjectConstant.getConnector().getConnect();
        PreparedStatement stmt = connect.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        boolean deleted = result.next();
        result.close();
        stmt.close();
        connect.commit();
        connect.close();
        return deleted;
    }
}
