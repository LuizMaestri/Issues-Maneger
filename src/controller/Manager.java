package controller;

import db.DBConnection;
import db.manipulate.UserDAO;
import exception.AuthenticationUserException;
import exception.ConnectionException;
import model.Issue;
import model.User;
import utils.Config;

import java.sql.SQLException;
import java.util.ArrayList;

public class Manager {

    private DBConnection connector;
    private User currentUser;
    private ArrayList<Issue> issues;

    public Manager() {
        setCurrentUser(new User());
        setConnector(Config.getDBConfig());
    }

    public void setConnector(DBConnection connector) {
        this.connector = connector;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    public boolean login(String user, String pass)
            throws AuthenticationUserException, ConnectionException {
        currentUser.setLogin(user);
        currentUser.setPass(pass);
        try {
            setCurrentUser(UserDAO.auth(currentUser, connector.getConnect()));
            if (currentUser != null) return true;
            currentUser = new User();
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            currentUser = new User();
            throw new ConnectionException("Impossivel Conectar com o banco:\n" + e.getClass());
        }
    }
}
