package com.anabneri.feedbacksystem.model;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Employees")
public class FeedbackEmployee {

    private String feedbackId;

    private  String employeeName;

    private Integer feedbackVersion;

    private List<EmployeeEntry> entries = new ArrayList<>();

    public FeedbackEmployee(Integer feedbackId) {}

    public String getFeedbackId() {
        return feedbackId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Integer getFeedbackVersion() {
        return feedbackVersion;
    }

    public List<EmployeeEntry> getEntries() {
        return entries;
    }

    public FeedbackEmployee(String feedbackId, String employeeName, Integer feedbackVersion) {
        this.feedbackId = feedbackId;
        this.employeeName = employeeName;
        this.feedbackVersion = feedbackVersion;


    }
}
