package com.anabneri.feedbacksystem.model;

import java.util.Date;

/**
 * EmployeeEntry is a HR management or another personal besides the employee who it's gonna do a Feedback
 * */
public class EmployeeEntry {

    // the username of the "feedbacker"
    private String username;

    // the date that the feedback was written
    private Date date;

    // the feedback content
    private String feedback;

    public EmployeeEntry() {
    }

    public EmployeeEntry(String username, Date date, String feedback) {
        this.username = username;
        this.date = date;
        this.feedback = feedback;
    }

    public EmployeeEntry(String username, String feedback) {
        this.username = username;
        this.feedback = feedback;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "EmployeeEntry{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
