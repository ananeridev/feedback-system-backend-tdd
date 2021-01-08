package com.anabneri.feedbacksystem.repository;

import com.anabneri.feedbacksystem.model.FeedbackEmployee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FeedbackRepository extends MongoRepository<FeedbackEmployee, String> {

    Optional<FeedbackEmployee> findByFeedbackId(Integer feedbackId);
}
