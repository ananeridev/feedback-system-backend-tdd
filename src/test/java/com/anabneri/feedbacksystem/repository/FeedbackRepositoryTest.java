package com.anabneri.feedbacksystem.repository;

import com.anabneri.feedbacksystem.model.EmployeeEntry;
import com.anabneri.feedbacksystem.model.FeedbackEmployee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.swing.text.html.Option;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
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

    /**
     * Jackson ObjectMapper: used to load a JSON file into a list of Reviews
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * The path to our Sample JSON file.
     */
    private static File SAMPLE_JSON = Paths.get("src", "test", "resources", "data", "sample.json").toFile();

    @BeforeEach
    void beforeEach() throws Exception {

        FeedbackEmployee[] objects = mapper.readValue(SAMPLE_JSON, FeedbackEmployee[].class);

        Arrays.stream(objects).forEach(mongoTemplate::save);
    }

    @AfterEach
    void afterEach() {

        mongoTemplate.dropCollection("Feedback");
    }

    @Test
    void testFindAll() {
        List<FeedbackEmployee> feedbackEmployees = repository.findAll();
        Assertions.assertEquals(2, feedbackEmployees.size(), "Should be two feedback's in the database");
    }

    @Test
    void testFindByIdSuccess() {
        Optional<FeedbackEmployee> feedbackEmployees = repository.findById("1");
        Assertions.assertTrue(feedbackEmployees.isPresent(), "We should have found a review with ID 1");
        feedbackEmployees.ifPresent(f -> {
            Assertions.assertEquals("1", f.getId(), "Feeback ID should be 1");
            Assertions.assertEquals(1, f.getFeedbackId().intValue(), "Feedback Employee ID should be 1");
            Assertions.assertEquals(1, f.getFeedbackVersion().intValue(), "Feedback version should be 1");
            Assertions.assertEquals(1, f.getEntries().size(), "Feedback 1 should have one entry");
        });
    }

    @Test
    void testFindByIdFailure() {
        Optional<FeedbackEmployee> feedbackEmployees = repository.findById("99");
        Assertions.assertFalse(feedbackEmployees.isPresent(), "We should not find a feedback with ID 99");
    }

    @Test
    void testFindByFeedbackIdSuccess() {
        Optional<FeedbackEmployee> feedbackEmployees = repository.findByFeedbackId(1);
        Assertions.assertTrue(feedbackEmployees.isPresent(), "There should be a feedback for product ID 1");
    }

    @Test
    void testFindByFeedbackIdFailure() {
        Optional<FeedbackEmployee> feedbackEmployees = repository.findByFeedbackId(99);
        Assertions.assertFalse(feedbackEmployees.isPresent(), "There should not be a feedback for product ID 99");
    }

    @Test
    void testSave() {

        FeedbackEmployee feedbackEmployees = new FeedbackEmployee(10, 1);
        feedbackEmployees.getEntries().add(new EmployeeEntry("test-user", new Date(), "This is a feedback"));

        FeedbackEmployee savedFeedback = repository.save(feedbackEmployees);

        Optional<FeedbackEmployee> loadedFeedback = repository.findById(savedFeedback.getId());

        Assertions.assertTrue(loadedFeedback.isPresent());
        loadedFeedback.ifPresent(f -> {
            Assertions.assertEquals(10, f.getFeedbackId().intValue());
            Assertions.assertEquals(1, f.getFeedbackVersion().intValue(), "Feedback version should be 1");
            Assertions.assertEquals(1, f.getEntries().size(), "Feedback 1 should have one entry");
        });
    }

    @Test
    void testUpdate() {

        Optional<FeedbackEmployee> feedbackEmployees = repository.findById("2");
        Assertions.assertTrue(feedbackEmployees.isPresent(), "Feedback 2 should be present");
        Assertions.assertEquals(3, feedbackEmployees.get().getEntries().size(), "There should be 3 feedback's employees entries");

        FeedbackEmployee feedbackToUpdate = feedbackEmployees.get();
        feedbackToUpdate.getEntries().add(new EmployeeEntry("test-user-2", new Date(), "This is a fourth feedback"));
        repository.save(feedbackToUpdate);

        Optional<FeedbackEmployee> updatedFeedback = repository.findById("2");
        Assertions.assertTrue(updatedFeedback.isPresent(), "Feedback 2 should be present");
        Assertions.assertEquals(4, updatedFeedback.get().getEntries().size(), "There should be 3 feedback's employees entries");
    }

    @Test
    void testDelete() {
        repository.deleteById("2");

        Optional<FeedbackEmployee> feedbackEmployees = repository.findById("2");
        Assertions.assertFalse(feedbackEmployees.isPresent(), "Feedback 2 should now be deleted from the database");
    }
}
