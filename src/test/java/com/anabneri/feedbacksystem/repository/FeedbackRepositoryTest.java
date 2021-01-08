package com.anabneri.feedbacksystem.repository;

import com.anabneri.feedbacksystem.model.EmployeeEntry;
import com.anabneri.feedbacksystem.model.FeedbackEmployee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Date;
import java.util.Optional;

@DataMongoTest
@ExtendWith(MongoSpringExtension.class)
public class FeedbackRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FeedbackRepository repository;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    @Test
    @MongoDataFile(value = "sample.json",classType = FeedbackEmployee.class, collectionName = "Employees")
    void testSave() {

        FeedbackEmployee feedbackEmployee = new FeedbackEmployee("10","Jacksson",1);
        feedbackEmployee.getEntries().add(new EmployeeEntry("girl-from-hr",new Date(),"this is a feedback :)"));

        FeedbackEmployee savedFeedback = repository.save(feedbackEmployee);

        Optional<FeedbackEmployee> loadedFeedback = repository.findById(savedFeedback.getFeedbackId());

        Assertions.assertTrue(loadedFeedback.isPresent());
        loadedFeedback.ifPresent(f -> {
            Assertions.assertEquals(10,f.getFeedbackVersion().intValue(),"feedback should be 1");,
            Assertions.assertEquals(10,f.getEntries().size(),"feedback should have only 1 entry");
        });

    }
}
