package controller;

import db.manipulate.IssueDAO;
import db.manipulate.SoftwareDAO;
import db.manipulate.UserDAO;
import exception.AuthenticationUserException;
import exception.ConnectionException;
import model.Issue;
import model.Software;
import model.User;
import model.enums.IssueCategory;
import utils.ProjectConstant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Manager {

    private User currentUser;
    private ArrayList<Issue> issues;
    private ArrayList<Software> softwares;

    public Manager() {
        setCurrentUser(new User());
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

    public ArrayList<Software> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(ArrayList<Software> softwares) {
        this.softwares = softwares;
    }

    public void reset() {
        this.setCurrentUser(new User());
        this.setIssues(null);
        this.setSoftwares(null);
    }

    public boolean login(String user, String pass)
            throws AuthenticationUserException, ConnectionException {
        this.currentUser.setLogin(user);
        this.currentUser.setPass(pass);
        try {
            setCurrentUser(UserDAO.auth(this.currentUser));
            if (this.currentUser != null){
                this.setSoftwares(SoftwareDAO.list());
                if (!this.currentUser.isAdmin()) this.setIssues(IssueDAO.list(this.currentUser));
                else this.setIssues(IssueDAO.list());
                return true;
            }
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
            return issues.add(IssueDAO.create(newIssue));
        } catch (SQLException | ClassNotFoundException e){
            return false;
        }
    }

    public boolean createSoftware(String name, String[] versions) {
        Software newSoftware = new Software();
        newSoftware.setName(name);
        newSoftware.setReleaseVersion(Integer.parseInt(versions[0]));
        newSoftware.setMinorVersion(Integer.parseInt(versions[1]));
        newSoftware.setFixVersion(Integer.parseInt(versions[2]));
        try {
            return softwares.add(SoftwareDAO.create(newSoftware));
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }
}
