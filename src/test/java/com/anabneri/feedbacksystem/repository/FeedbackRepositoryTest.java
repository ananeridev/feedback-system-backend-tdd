package com.anabneri.feedbacksystem.repository;

import com.anabneri.feedbacksystem.model.EmployeeEntry;
import com.anabneri.feedbacksystem.model.FeedbackEmployee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
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
            Assertions.assertEquals(10,f.getFeedbackVersion().intValue(),"feedback should be 1");
            Assertions.assertEquals(10,f.getEntries().size(),"feedback should have only 1 entry");
        });

    }

    @Test
    @MongoDataFile(value = "sample.json",classType = FeedbackEmployee.class, collectionName = "Employees")
    void TestFindAll() {
        List<FeedbackEmployee> feedbacks = repository.findAll();
        Assertions.assertEquals(2,feedbacks.size(), "Should be two feedbacks");
        feedbacks.forEach(System.out::println);
    }


    @Test
    @MongoDataFile(value = "sample.json",classType = FeedbackEmployee.class, collectionName = "Employees")
    void testFindFeedbackIdSuccess() {
        Optional<FeedbackEmployee> feedbackEmployee = repository.findByFeedbackId(1);
        Assertions.assertTrue(feedbackEmployee.isPresent(), "There should be a feedback for employee 1");
    }

    @Test
    @MongoDataFile(value = "sample.json",classType = FeedbackEmployee.class, collectionName = "Employees")
    void testFindByFeedbackIdFailure() {
        Optional<FeedbackEmployee> feedbackEmployee = repository.findByFeedbackId(99);
        Assertions.assertTrue(feedbackEmployee.isPresent(), "There shouldn't be a feedback for employee with the ID 99" );
    }
}
