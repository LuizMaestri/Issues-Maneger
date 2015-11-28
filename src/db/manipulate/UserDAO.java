package db.manipulate;

import exception.AuthenticationUserException;
import model.User;
import model.enums.UserType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO{
    public static boolean create(User newUser, Connection connect)
            throws SQLException {
        String sql =
                "INSERT INTO users " +
                "VALUES (?, md5(?), ?, ?) " +
                "RETURNING nm_login;";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, newUser.getLogin());
        stmt.setString(2, newUser.getPass());
        stmt.setString(3, newUser.getName());
        stmt.setInt(4, newUser.getType().ordinal());
        try {
            stmt.executeQuery();
            stmt.close();
            connect.commit();
            connect.close();
            return true;
        } catch (SQLException e) {
            stmt.close();
            connect.close();
            return false;
        }
    }

    public static boolean update(User toUpdate, Connection connect)
            throws SQLException{
        String sql =
                "UPDATE users " +
                "SET nm_login=?, nm_pass=md5(?), nm_user=?, fl_user=? " +
                "WHERE nm_login=? " +
                "RETURNING nm_login;";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, toUpdate.getLogin());
        stmt.setString(2, toUpdate.getPass());
        stmt.setString(3, toUpdate.getName());
        stmt.setInt(4, toUpdate.getType().ordinal());
        stmt.setString(5, toUpdate.getLogin());
        ResultSet result = stmt.executeQuery();
        boolean updated = result.next();
        result.close();
        stmt.close();
        connect.commit();
        connect.close();
        return updated;
    }

    public  static boolean delete(String user, Connection connect)
            throws SQLException{
        String sql =
                "DELETE FROM users " +
                "WHERE nm_login=? " +
                "RETURNING nm_login;";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, user);
        try {
            ResultSet result = stmt.executeQuery();
            boolean deleted = result.next();
            stmt.close();
            connect.commit();
            connect.close();
            return deleted;
        } catch (SQLException e) {
            stmt.close();
            connect.close();
            return false;
        }
    }

    public static User auth(User authUser, Connection connect)
            throws SQLException, AuthenticationUserException {
        String sql =
                "SELECT * " +
                "FROM users " +
                "WHERE nm_login=? AND nm_pass=md5(?)";
        PreparedStatement stmt = connect.prepareStatement(sql);
        stmt.setString(1, authUser.getLogin());
        stmt.setString(2, authUser.getPass());
        try {
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                authUser.setName(result.getString("nm_user"));
                authUser.setType(UserType.getType(result.getInt("fl_user")));
            }
            result.close();
            stmt.close();
            connect.close();
            if(authUser.getName() != null) return authUser;
            throw new AuthenticationUserException("Login/senha errado(s)");
        }catch (SQLException e){
            throw new AuthenticationUserException("Login/senha errado(s)");
        }

    }
}