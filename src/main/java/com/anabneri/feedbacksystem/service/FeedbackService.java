package com.anabneri.feedbacksystem.service;

import com.anabneri.feedbacksystem.model.Employee;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    Optional<Employee> findById(String id);

    Optional<Employee> findByFeedbackId(Integer feedbackId);

    List<Employee> findAll();

    Employee save(Employee employee);

    Employee update(Employee employee);

    void delete(String id);
}
