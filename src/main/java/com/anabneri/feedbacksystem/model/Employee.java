package com.anabneri.feedbacksystem.model;


import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Employees")
public class Employee {

    private String employeeId;

    private  String employeeName;

    private Integer feedbackVersion;

    private List<EmployeeEntry> entries = new ArrayList<>();

    public Employee(Integer employeeId) {}

    public String getEmployeeId() {
        return employeeId;
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

    public Employee(String employeeId, String employeeName, Integer feedbackVersion) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.feedbackVersion = feedbackVersion;


    }
}
