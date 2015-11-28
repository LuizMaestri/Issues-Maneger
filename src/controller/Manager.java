package controller;

import db.DBConnection;
import db.manipulate.IssueDAO;
import db.manipulate.UserDAO;
import exception.AuthenticationUserException;
import exception.ConnectionException;
import model.Issue;
import model.User;
import model.enums.IssueCategory;
import utils.Config;
import utils.ProjectConstant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

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

    public void reset() {
        this.setCurrentUser(new User());
        this.setIssues(null);
    }

    public boolean login(String user, String pass)
            throws AuthenticationUserException, ConnectionException {
        this.currentUser.setLogin(user);
        this.currentUser.setPass(pass);
        try {
            setCurrentUser(UserDAO.auth(this.currentUser, this.connector.getConnect()));
            if (this.currentUser != null) return true;
            this.currentUser = new User();
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            this.currentUser = new User();
            throw new ConnectionException("Impossivel Conectar com o banco:\n" + e.getClass());
        }
    }

    public boolean createIssue(String name, String description, IssueCategory category, Date deadline) {
        Issue newIssue = new Issue();
        newIssue.setRequester(ProjectConstant.getManager().getCurrentUser());
        newIssue.setName(name);
        newIssue.setDescription(description);
        newIssue.setCategory(category);
        newIssue.setDeadline(deadline);
        try {
            return issues.add(IssueDAO.create(newIssue, this.connector.getConnect()));
        } catch (SQLException | ClassNotFoundException e){
            return false;
        }
    }
}
