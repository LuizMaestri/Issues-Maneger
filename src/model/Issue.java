package model;

import model.enums.Category;
import model.enums.Status;

import java.text.DateFormat;
import java.util.Date;

public class Issue {
    private long uuid;
    private String name;
    private String description;
    private Status status;
    private Date start;
    private Date deadline;
    private Date over;
    private User requester;
    private User approving;
    private User maker;
    private Category type;

    public Issue(User requester) {
        setRequester(requester);
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getOver() {
        return over;
    }

    public void setOver(Date over) {
        this.over = over;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getApproving() {
        return approving;
    }

    public void setApproving(User approving) {
        this.approving = approving;
    }

    public User getMaker() {
        return maker;
    }

    public void setMaker(User maker) {
        this.maker = maker;
    }

    public Category getType() {
        return type;
    }

    public void setType(Category type) {
        this.type = type;
    }

    public boolean overdue(){
        return over == null && deadline != null && new Date().after(deadline);
    }

    public String toString(){
        String info = "";
        info += uuid + " | ";
        info += name + " | ";
        info += description + " | ";
        info += status != null? status.name() + " | " : " | ";
        info += start != null? DateFormat.getDateTimeInstance().format(start) + " | " : " | ";
        info += deadline != null? DateFormat.getDateTimeInstance().format(deadline) + " | " : " | ";
        info += over != null? DateFormat.getDateTimeInstance().format(over) +" | " : " | ";
        info += requester.getName() + " | ";
        info += approving!=null? approving.getName() + " | " : " | ";
        info += maker!=null? maker.getName() + " | " : " | ";
        info += type.name().replace('_', ' ');
        return info;
    }
}
