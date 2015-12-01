package model;

import java.util.Date;

/**
 * Created by luiz on 29/11/15.
 */
public class Comment {
    private Long uuid;
    private Long IssueId;
    private User user;
    private String comment;
    private Date date;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getIssueId() {
        return IssueId;
    }

    public void setIssueId(Long issueId) {
        IssueId = issueId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
