package model;

import model.enums.IssueCategory;
import model.enums.IssueStatus;

import java.text.DateFormat;
import java.util.Date;

public class Issue {
    private long uuid;
    private String name;
    private String description;
    private IssueStatus issueStatus;
    private Date create;
    private Date start;
    private Date deadline;
    private Date over;
    private User requester;
    private User approving;
    private User maker;
    private IssueCategory category;
    private Software software;

    public Issue() {

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

    public IssueStatus getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(IssueStatus issueStatus) {
        this.issueStatus = issueStatus;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
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

    public IssueCategory getCategory() {
        return category;
    }

    public void setCategory(IssueCategory category) {
        this.category = category;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public boolean overdue(){
        return over == null && deadline != null && new Date().after(deadline);
    }

    public String toString(){
        String info = "";
        info += uuid + " | ";
        info += name + " | ";
        info += description + " | ";
        info += issueStatus != null? issueStatus.name() + " | " : " | ";
        info += start != null? DateFormat.getDateTimeInstance().format(start) + " | " : " | ";
        info += deadline != null? DateFormat.getDateTimeInstance().format(deadline) + " | " : " | ";
        info += over != null? DateFormat.getDateTimeInstance().format(over) +" | " : " | ";
        info += requester.getName() + " | ";
        info += approving!=null? approving.getName() + " | " : " | ";
        info += maker!=null? maker.getName() + " | " : " | ";
        info += category.name().replace('_', ' ');
        return info;
    }
}
