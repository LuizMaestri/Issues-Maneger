package model;

import model.enums.UserType;

public class User {
    private String login;
    private String pass;
    private String name;
    private UserType type;

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public boolean isApproving(){
        return type == UserType.getType(1);
    }

    public boolean isAdmin(){
        return type == UserType.ADMIN;
    }

    public boolean isDeveloper(){
        return type == UserType.getType(0);
    }

    public boolean isAnalyst(){
        return type == UserType.getType(2);
    }
}
