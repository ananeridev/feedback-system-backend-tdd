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
    public FeedbackEmployee save(FeedbackEmployee feedbackEmployee) {
        feedbackEmployee.setFeedbackVersion(1);
        return repository.save(feedbackEmployee);
    }

    @Override
    public FeedbackEmployee update(FeedbackEmployee feedbackEmployee) {
        feedbackEmployee.setFeedbackVersion(feedbackEmployee.getFeedbackVersion()+1);
        return repository.save(feedbackEmployee);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
