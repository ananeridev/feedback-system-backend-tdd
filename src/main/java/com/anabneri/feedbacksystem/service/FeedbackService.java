package com.anabneri.feedbacksystem.service;

import com.anabneri.feedbacksystem.model.FeedbackEmployee;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    Optional<FeedbackEmployee> findById(String id);

    Optional<FeedbackEmployee> findByFeedbackId(Integer feedbackId);

    List<FeedbackEmployee> findAll();

    FeedbackEmployee save(FeedbackEmployee employee);

    FeedbackEmployee update(FeedbackEmployee employee);

    void delete(String id);
}
