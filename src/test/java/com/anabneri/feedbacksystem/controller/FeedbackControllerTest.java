package com.anabneri.feedbacksystem.controller;

import com.anabneri.feedbacksystem.model.EmployeeEntry;
import com.anabneri.feedbacksystem.model.Employee;
import com.anabneri.feedbacksystem.service.FeedbackService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackControllerTest {

    @MockBean
    private FeedbackService service;

    @Autowired
    private MockMvc mockMvc;

    // create the DateFormat to set a Date and Hour that I can use to compare SpringMvc returned the dates to expected values
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    @BeforeAll
    static void beforeAll() {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Test
    @DisplayName("GET /feedback/feedbackId - THE FEEDBACK WAS FOUND")
    void testFeedbackByIdFound() throws Exception {

        Employee mockEmployee = new Employee("1","Jacksson",1);
        Date now = new Date();
        mockEmployee.getEntries().add(new EmployeeEntry("joana_from_HR",now,
                "Jacksson it's a great employee, very insightful"));



    }

    @Test
    @DisplayName("GET /feedback/feedbackId - THE FEEDBACK WAS NOT FOUND")
    void testFeedbackByIdNotFound() throws Exception {

    }

    @Test
    @DisplayName("POST /feedback - SUCCESS")
    void testFeedback() throws Exception {

    }

    @Test
    @DisplayName("GET /feedback/{employeeId}/entry - FEEDBACK BY EMPLOYEE ENTRY")
    void testFeedbackByIdFound() throws Exception {

    }

    @Test
    @DisplayName("GET /feedback/{employeeId}/entry - FEEDBACK BY EMPLOYEE ENTRY")
    void testFeedbackByIdFound() throws Exception {

    }
}
