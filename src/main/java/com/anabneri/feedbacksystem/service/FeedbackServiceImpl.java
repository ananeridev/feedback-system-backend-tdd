package com.anabneri.feedbacksystem.service;

import com.anabneri.feedbacksystem.model.FeedbackEmployee;
import com.anabneri.feedbacksystem.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackRepository repository;

    public FeedbackServiceImpl(FeedbackRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<FeedbackEmployee> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<FeedbackEmployee> findByFeedbackId(Integer feedbackId) {
        return repository.findByFeedbackId(feedbackId);
    }

    @Override
    public List<FeedbackEmployee> findAll() {
        return repository.findAll();
    }

    @Override
    public FeedbackEmployee save(FeedbackEmployee feedback) {
        feedback.setFeedbackVersion(1);
        return repository.save(feedback);
    }

    @Override
    public FeedbackEmployee update(FeedbackEmployee feedback) {
        feedback.setFeedbackVersion(feedback.getFeedbackVersion()+1);
        return repository.save(feedback);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}

