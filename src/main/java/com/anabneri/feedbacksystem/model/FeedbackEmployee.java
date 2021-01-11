package com.anabneri.feedbacksystem.model;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Feedback's")
public class FeedbackEmployee {

    private String id;

    private  Integer feedbackId;

    private Integer feedbackVersion = 1;

    private List<EmployeeEntry> entries = new ArrayList<>();

    public FeedbackEmployee(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public FeedbackEmployee(String id, Integer feedbackId, Integer version) {
        this.id = id;
        this.feedbackId = feedbackId;
        this.feedbackVersion = feedbackVersion;
    }

    public FeedbackEmployee(Integer feedbackId, Integer version) {
        this.feedbackId = feedbackId;
        this.feedbackVersion = feedbackVersion;
    }

    public FeedbackEmployee(String id, Integer feedbackId) {
        this.id = id;
        this.feedbackId = feedbackId;
    }

    public String getId() {
        return id;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public Integer getFeedbackVersion() {
        return feedbackVersion;
    }

    public void setFeedbackVersion(Integer feedbackVersion) {
        this.feedbackVersion = feedbackVersion;
    }

    public List<EmployeeEntry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "FeedbackEmployee{" +
                "id='" + id + '\'' +
                ", feedbackId=" + feedbackId +
                ", feedbackVersion=" + feedbackVersion +
                ", entries=" + entries +
                '}';
    }
}
