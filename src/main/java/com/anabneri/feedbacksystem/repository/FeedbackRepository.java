package com.anabneri.feedbacksystem.repository;

import com.anabneri.feedbacksystem.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FeedbackRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByFeedbackId(Integer feedbackId);
}
