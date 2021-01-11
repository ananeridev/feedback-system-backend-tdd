package com.anabneri.feedbacksystem.controller;

import com.anabneri.feedbacksystem.model.EmployeeEntry;
import com.anabneri.feedbacksystem.model.FeedbackEmployee;
import com.anabneri.feedbacksystem.service.FeedbackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;


@RestController
public class FeedbackController {

    private static final Logger logger = LogManager.getLogger(FeedbackController.class);

    private FeedbackService service;

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @GetMapping("feedback/{id}")
    public ResponseEntity<?> getFeedback(@PathVariable String id) {
        return service.findById(id)
                .map(feedback -> {
                    try {
                        return ResponseEntity
                                .ok()
                                .eTag(Integer.toString(feedback.getFeedbackVersion()))
                                .location(new URI("/feedback" + feedback.getFeedbackId()))
                                .body(feedback);
                    } catch (URISyntaxException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/feedback")
    public Iterable<FeedbackEmployee> getFeedback(@RequestParam(value = "feedbackId", required = false) Optional<String> feedbackId) {
        return feedbackId.map(fid -> {
            return service.findByFeedbackId(Integer.valueOf(fid))
                    .map(Arrays::asList)
                    .orElseGet(ArrayList::new);
        }).orElse(service.findAll());
    }

    @PostMapping("/feedback")
    public ResponseEntity<FeedbackEmployee> createFeedback(@RequestBody FeedbackEmployee feedbackEmployee) {
        logger.info("Creating new feedback for employee id: {}, {}", feedbackEmployee.getFeedbackId(), feedbackEmployee);

        feedbackEmployee.getEntries().forEach(employeeEntry -> employeeEntry.setDate(new Date()));

        FeedbackEmployee newFeedback = service.save(feedbackEmployee);
        logger.info("Saved Feedback: {}", newFeedback);

        try {
            return ResponseEntity
                    .created(new URI("/feedback/" + newFeedback.getFeedbackId()))
                    .eTag(Integer.toString(newFeedback.getFeedbackVersion()))
                    .body(newFeedback);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/feedback/{feedbackId}/entry")
    public ResponseEntity<FeedbackEmployee> addEntryToFeedback(@PathVariable Integer feedbackId, @RequestBody EmployeeEntry entry) {
        logger.info("Add feedback entry for employee id: {}, {}", feedbackId, entry);

        FeedbackEmployee feedbackEmployee = service.findByFeedbackId(feedbackId).orElseGet(() -> new FeedbackEmployee(feedbackId));

        entry.setDate(new Date());
        feedbackEmployee.getEntries().add(entry);


        FeedbackEmployee updatedFeedback = service.save(feedbackEmployee);
        logger.info("Updated feedback: {}", updatedFeedback);

        try {

            return ResponseEntity
                    .ok()
                    .location(new URI("/feedback/" + updatedFeedback.getFeedbackId()))
                    .eTag(Integer.toString(updatedFeedback.getFeedbackVersion()))
                    .body(updatedFeedback);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable String id) {
        logger.info("Deleting feedback with ID {}", id);

        Optional<FeedbackEmployee> existingFeedback = service.findById(id);

        return existingFeedback.map(feedbackEmployee -> {
            service.delete(feedbackEmployee.getId());
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());

    }

}



